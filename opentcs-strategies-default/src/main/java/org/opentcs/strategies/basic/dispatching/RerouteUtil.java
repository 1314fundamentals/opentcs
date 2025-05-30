// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching;

import static java.util.Objects.requireNonNull;
import static org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration.ReroutingImpossibleStrategy.IGNORE_PATH_LOCKS;

import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import org.opentcs.components.kernel.Router;
import org.opentcs.components.kernel.services.InternalTransportOrderService;
import org.opentcs.data.model.Path;
import org.opentcs.data.model.Point;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.order.DriveOrder;
import org.opentcs.data.order.ReroutingType;
import org.opentcs.data.order.Route;
import org.opentcs.data.order.Route.Step;
import org.opentcs.data.order.TransportOrder;
import org.opentcs.data.peripherals.PeripheralJob;
import org.opentcs.drivers.vehicle.VehicleController;
import org.opentcs.drivers.vehicle.VehicleControllerPool;
import org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration.ReroutingImpossibleStrategy;
import org.opentcs.strategies.basic.dispatching.rerouting.ReroutingStrategy;
import org.opentcs.strategies.basic.dispatching.rerouting.VehiclePositionResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides some utility methods used for rerouting vehicles.
 */
public class RerouteUtil {

  /**
   * This class's logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RerouteUtil.class);
  /**
   * The router.
   */
  private final Router router;
  /**
   * The vehicle controller pool.
   */
  private final VehicleControllerPool vehicleControllerPool;
  /**
   * The object service.
   */
  private final InternalTransportOrderService transportOrderService;

  private final DefaultDispatcherConfiguration configuration;

  private final Map<ReroutingType, ReroutingStrategy> reroutingStrategies;

  private final VehiclePositionResolver vehiclePositionResolver;

  /**
   * Creates a new instance.
   *
   * @param router The router.
   * @param vehicleControllerPool The vehicle controller pool.
   * @param transportOrderService The object service.
   * @param configuration The configuration.
   * @param reroutingStrategies The rerouting strategies to select from.
   * @param vehiclePositionResolver Used to resolve the position of vehicles.
   */
  @Inject
  public RerouteUtil(
      Router router,
      VehicleControllerPool vehicleControllerPool,
      InternalTransportOrderService transportOrderService,
      DefaultDispatcherConfiguration configuration,
      Map<ReroutingType, ReroutingStrategy> reroutingStrategies,
      VehiclePositionResolver vehiclePositionResolver
  ) {
    this.router = requireNonNull(router, "router");
    this.vehicleControllerPool = requireNonNull(vehicleControllerPool, "vehicleControllerPool");
    this.transportOrderService = requireNonNull(transportOrderService, "transportOrderService");
    this.configuration = requireNonNull(configuration, "configuration");
    this.reroutingStrategies = requireNonNull(reroutingStrategies, "reroutingStrategies");
    this.vehiclePositionResolver = requireNonNull(
        vehiclePositionResolver,
        "vehiclePositionResolver"
    );
  }

  public void reroute(Collection<Vehicle> vehicles, ReroutingType reroutingType) {
    for (Vehicle vehicle : vehicles) {
      reroute(vehicle, reroutingType);
    }
  }

  public void reroute(Vehicle vehicle, ReroutingType reroutingType) {
    requireNonNull(vehicle, "vehicle");
    LOG.debug("Trying to reroute vehicle '{}'...", vehicle.getName());

    if (!vehicle.isProcessingOrder()) {
      LOG.debug("{} can't be rerouted without processing a transport order.", vehicle.getName());
      return;
    }

    TransportOrder originalOrder = transportOrderService.fetchObject(
        TransportOrder.class,
        vehicle.getTransportOrder()
    );

    if (originalOrder.hasState(TransportOrder.State.WITHDRAWN)) {
      LOG.warn("{} can't be rerouted when its transport order was withdrawn.", vehicle.getName());
      return;
    }

    if (reroutingType == ReroutingType.FORCED
        && isRelatedToUnfinishedPeripheralJobs(originalOrder)) {
      LOG.warn(
          "Cannot reroute {} when there are unfinished peripheral jobs "
              + "related to the current transport order.",
          vehicle.getName()
      );
      return;
    }

    Optional<List<DriveOrder>> optOrders;
    if (reroutingStrategies.containsKey(reroutingType)) {
      optOrders = reroutingStrategies.get(reroutingType).reroute(vehicle);
    }
    else {
      LOG.warn(
          "Cannot reroute {} for unknown rerouting type: {}",
          vehicle.getName(),
          reroutingType.name()
      );
      optOrders = Optional.empty();
    }

    if (reroutingType == ReroutingType.FORCED && vehicle.getState() != Vehicle.State.IDLE) {
      LOG.warn(
          "Forcefully rerouting {} although its state is not 'IDLE' but '{}'.",
          vehicle.getName(),
          vehicle.getState().name()
      );
    }

    // Get the drive order with the new route or stick to the old one
    List<DriveOrder> newDriveOrders;
    if (optOrders.isPresent()) {
      newDriveOrders = optOrders.get();
    }
    else {
      newDriveOrders = updatePathLocksAndRestrictions(vehicle, originalOrder);
    }

    LOG.debug("Updating transport order {}...", originalOrder.getName());
    updateTransportOrder(originalOrder, newDriveOrders, vehicle);
  }

  private List<DriveOrder> updatePathLocksAndRestrictions(
      Vehicle vehicle,
      TransportOrder originalOrder
  ) {
    LOG.debug(
        "Couldn't find a new route for {}. Updating the current one...",
        vehicle.getName()
    );
    // Get all unfinished drive order of the transport order the vehicle is processing
    List<DriveOrder> unfinishedOrders = new ArrayList<>();
    unfinishedOrders.add(originalOrder.getCurrentDriveOrder());
    unfinishedOrders.addAll(originalOrder.getFutureDriveOrders());

    unfinishedOrders = updatePathLocks(unfinishedOrders);
    unfinishedOrders = markRestrictedSteps(
        unfinishedOrders,
        new ExecutionTest(
            configuration.reroutingImpossibleStrategy(),
            vehiclePositionResolver.getFutureOrCurrentPosition(vehicle)
        )
    );
    return unfinishedOrders;
  }

  private void updateTransportOrder(
      TransportOrder originalOrder,
      List<DriveOrder> newDriveOrders,
      Vehicle vehicle
  ) {
    VehicleController controller = vehicleControllerPool.getVehicleController(vehicle.getName());

    // Restore the transport order's history
    List<DriveOrder> newOrders = new ArrayList<>();
    newOrders.addAll(originalOrder.getPastDriveOrders());
    newOrders.addAll(newDriveOrders);

    // Update the transport order's drive orders with the re-routed ones
    LOG.debug("{}: Updating drive orders with {}.", originalOrder.getName(), newOrders);
    transportOrderService.updateTransportOrderDriveOrders(
        originalOrder.getReference(),
        newOrders
    );

    // If the vehicle is currently processing a (drive) order (and not waiting to get the next
    // drive order) we need to update the vehicle's current drive order with the new one.
    if (vehicle.hasProcState(Vehicle.ProcState.PROCESSING_ORDER)) {
      controller.setTransportOrder(
          transportOrderService.fetchObject(TransportOrder.class, originalOrder.getReference())
      );
    }
  }

  private List<DriveOrder> updatePathLocks(List<DriveOrder> orders) {
    List<DriveOrder> updatedOrders = new ArrayList<>();

    for (DriveOrder order : orders) {
      List<Step> updatedSteps = new ArrayList<>();

      for (Step step : order.getRoute().getSteps()) {
        if (step.getPath() != null) {
          updatedSteps.add(
              step.withPath(
                  transportOrderService.fetchObject(Path.class, step.getPath().getReference())
              )
          );
        }
        else {
          // If the step doesn't have a path, there are no path locks to be updated and we can
          // simply keep the step as it is.
          updatedSteps.add(step);
        }
      }

      Route updatedRoute = new Route(updatedSteps);

      DriveOrder updatedOrder = new DriveOrder(order.getName(), order.getDestination())
          .withRoute(updatedRoute)
          .withState(order.getState())
          .withTransportOrder(order.getTransportOrder());
      updatedOrders.add(updatedOrder);
    }

    return updatedOrders;
  }

  private List<DriveOrder> markRestrictedSteps(
      List<DriveOrder> orders,
      Predicate<Step> executionTest
  ) {
    if (configuration.reroutingImpossibleStrategy() == IGNORE_PATH_LOCKS) {
      return orders;
    }
    if (!containsLockedPath(orders)) {
      return orders;
    }

    List<DriveOrder> updatedOrders = new ArrayList<>();
    for (DriveOrder order : orders) {
      List<Step> updatedSteps = new ArrayList<>();

      for (Step step : order.getRoute().getSteps()) {
        boolean executionAllowed = executionTest.test(step);
        LOG.debug("Marking path '{}' allowed: {}", step.getPath(), executionAllowed);
        updatedSteps.add(step.withExecutionAllowed(executionAllowed));
      }

      Route updatedRoute = new Route(updatedSteps);

      DriveOrder updatedOrder = new DriveOrder(order.getName(), order.getDestination())
          .withRoute(updatedRoute)
          .withState(order.getState())
          .withTransportOrder(order.getTransportOrder());
      updatedOrders.add(updatedOrder);
    }

    return updatedOrders;
  }

  private boolean containsLockedPath(List<DriveOrder> orders) {
    return orders.stream()
        .map(order -> order.getRoute().getSteps())
        .flatMap(steps -> steps.stream())
        .filter(step -> step.getPath() != null)
        .anyMatch(step -> step.getPath().isLocked());
  }

  private boolean isRelatedToUnfinishedPeripheralJobs(TransportOrder transportOrder) {
    return !transportOrderService.fetchObjects(
        PeripheralJob.class,
        job -> Objects.equals(job.getRelatedTransportOrder(), transportOrder.getReference())
            && job.getPeripheralOperation().isCompletionRequired()
            && !job.getState().isFinalState()
    ).isEmpty();
  }

  private class ExecutionTest
      implements
        Predicate<Step> {

    /**
     * The current fallback strategy.
     */
    private final ReroutingImpossibleStrategy strategy;
    /**
     * The (earliest) point from which execution may not be allowed.
     */
    private final Point source;
    /**
     * Whether execution of a step is allowed.
     */
    private boolean executionAllowed = true;

    /**
     * Creates a new instance.
     *
     * @param strategy The current fallback strategy.
     * @param source The (earliest) point from which execution may not be allowed.
     */
    ExecutionTest(ReroutingImpossibleStrategy strategy, Point source) {
      this.strategy = requireNonNull(strategy, "strategy");
      this.source = requireNonNull(source, "source");
    }

    @Override
    public boolean test(Step step) {
      if (!executionAllowed) {
        return false;
      }

      switch (strategy) {
        case PAUSE_IMMEDIATELY:
          if (Objects.equals(step.getSourcePoint(), source)) {
            executionAllowed = false;
          }
          break;
        case PAUSE_AT_PATH_LOCK:
          if (step.getPath() != null && step.getPath().isLocked()) {
            executionAllowed = false;
          }
          break;
        default:
          executionAllowed = true;
      }

      return executionAllowed;
    }
  }
}
