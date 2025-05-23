// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.selection.vehicles;

import static java.util.Objects.requireNonNull;

import jakarta.inject.Inject;
import java.util.function.Predicate;
import org.opentcs.components.kernel.services.TCSObjectService;
import org.opentcs.data.ObjectHistory;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.order.OrderSequence;
import org.opentcs.data.order.TransportOrder;
import org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration;
import org.opentcs.strategies.basic.dispatching.OrderReservationPool;
import org.opentcs.strategies.basic.dispatching.selection.VehicleSelectionFilter;

/**
 * Filters vehicles that are generally available for transport orders.
 *
 * <p>
 * Note: This filter is not a {@link VehicleSelectionFilter} by intention, since it is not
 * intended to be used in contexts where {@link ObjectHistory} entries are created.
 * </p>
 */
public class IsAvailableForAnyOrder
    implements
      Predicate<Vehicle> {

  /**
   * The object service.
   */
  private final TCSObjectService objectService;
  /**
   * Stores reservations of orders for vehicles.
   */
  private final OrderReservationPool orderReservationPool;
  /**
   * The default dispatcher configuration.
   */
  private final DefaultDispatcherConfiguration configuration;

  /**
   * Creates a new instance.
   *
   * @param objectService The object service.
   * @param orderReservationPool Stores reservations of orders for vehicles.
   * @param configuration The default dispatcher configuration.
   */
  @Inject
  public IsAvailableForAnyOrder(
      TCSObjectService objectService,
      OrderReservationPool orderReservationPool,
      DefaultDispatcherConfiguration configuration
  ) {
    this.objectService = requireNonNull(objectService, "objectService");
    this.orderReservationPool = requireNonNull(orderReservationPool, "orderReservationPool");
    this.configuration = requireNonNull(configuration, "configuration");
  }

  @Override
  public boolean test(Vehicle vehicle) {
    return vehicle.getIntegrationLevel() == Vehicle.IntegrationLevel.TO_BE_UTILIZED
        && vehicle.getCurrentPosition() != null
        && !needsMoreCharging(vehicle)
        && !hasOrderReservation(vehicle)
        && !vehicle.isPaused()
        && processesNoOrDispensableOrder(vehicle)
        && (vehicle.getOrderSequence() == null
            || processesDispensableLastOrderInOrderSequence(vehicle));
  }

  private boolean needsMoreCharging(Vehicle vehicle) {
    return vehicle.hasState(Vehicle.State.CHARGING)
        && !rechargeThresholdReached(vehicle);
  }

  private boolean rechargeThresholdReached(Vehicle vehicle) {
    return configuration.keepRechargingUntilFullyCharged()
        ? vehicle.isEnergyLevelFullyRecharged()
        : vehicle.isEnergyLevelSufficientlyRecharged();
  }

  private boolean processesNoOrDispensableOrder(Vehicle vehicle) {
    return vehicle.hasProcState(Vehicle.ProcState.IDLE)
        && (vehicle.hasState(Vehicle.State.IDLE) || vehicle.hasState(Vehicle.State.CHARGING))
        || vehicle.hasProcState(Vehicle.ProcState.PROCESSING_ORDER)
            && objectService.fetchObject(TransportOrder.class, vehicle.getTransportOrder())
                .isDispensable();
  }

  private boolean processesDispensableLastOrderInOrderSequence(Vehicle vehicle) {
    if (vehicle.hasProcState(Vehicle.ProcState.PROCESSING_ORDER)
        && vehicle.getOrderSequence() != null) {
      OrderSequence seq = objectService.fetchObject(
          OrderSequence.class, vehicle.getOrderSequence()
      );
      return seq.isComplete()
          && seq.getOrders().getLast().equals(vehicle.getTransportOrder())
          && objectService.fetchObject(TransportOrder.class, vehicle.getTransportOrder())
              .isDispensable();
    }
    return false;
  }

  private boolean hasOrderReservation(Vehicle vehicle) {
    return !orderReservationPool.findReservations(vehicle.getReference()).isEmpty();
  }
}
