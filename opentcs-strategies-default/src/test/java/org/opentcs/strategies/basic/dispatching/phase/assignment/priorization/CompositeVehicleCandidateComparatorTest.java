// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.phase.assignment.priorization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.opentcs.data.model.Point;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.order.DriveOrder;
import org.opentcs.data.order.Route;
import org.opentcs.data.order.TransportOrder;
import org.opentcs.strategies.basic.dispatching.AssignmentCandidate;
import org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration;
import org.opentcs.strategies.basic.dispatching.priorization.CompositeVehicleCandidateComparator;
import org.opentcs.strategies.basic.dispatching.priorization.candidate.CandidateComparatorByInitialRoutingCosts;

/**
 */
class CompositeVehicleCandidateComparatorTest {

  private CompositeVehicleCandidateComparator comparator;
  private DefaultDispatcherConfiguration configuration;
  private Map<String, Comparator<AssignmentCandidate>> availableComparators;

  @BeforeEach
  void setUp() {
    configuration = Mockito.mock(DefaultDispatcherConfiguration.class);
    availableComparators = new HashMap<>();
  }

  @Test
  void sortNamesUpForOtherwiseEqualInstances() {

    Mockito.when(configuration.vehicleCandidatePriorities())
        .thenReturn(List.of());
    comparator = new CompositeVehicleCandidateComparator(configuration, availableComparators);

    AssignmentCandidate candidate1 = candidateWithName("AA");
    AssignmentCandidate candidate2 = candidateWithName("CC");
    AssignmentCandidate candidate3 = candidateWithName("AB");

    List<AssignmentCandidate> list = new ArrayList<>();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);

    Collections.sort(list, comparator);

    assertThat(list.get(0), is(theInstance(candidate1)));
    assertThat(list.get(1), is(theInstance(candidate3)));
    assertThat(list.get(2), is(theInstance(candidate2)));
  }

  @Test
  void sortsByNameAndEnergylevel() {
    Mockito.when(configuration.vehicleCandidatePriorities())
        .thenReturn(List.of());
    comparator = new CompositeVehicleCandidateComparator(configuration, availableComparators);

    AssignmentCandidate candidate1 = candidateWithNameEnergylevel("AA", 1);
    AssignmentCandidate candidate2 = candidateWithNameEnergylevel("CC", 2);
    AssignmentCandidate candidate3 = candidateWithNameEnergylevel("BB", 2);

    List<AssignmentCandidate> list = new ArrayList<>();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);

    Collections.sort(list, comparator);

    assertThat(list.get(0), is(theInstance(candidate3)));
    assertThat(list.get(1), is(theInstance(candidate2)));
    assertThat(list.get(2), is(theInstance(candidate1)));
  }

  @Test
  void sortsByNameAndRoutingCostAndEnergyLevel() {
    String initRoutingCostKey = "BY_INITIAL_ROUTING_COSTS";
    Mockito.when(configuration.vehicleCandidatePriorities())
        .thenReturn(List.of(initRoutingCostKey));
    availableComparators.put(
        initRoutingCostKey,
        new CandidateComparatorByInitialRoutingCosts()
    );

    comparator = new CompositeVehicleCandidateComparator(configuration, availableComparators);

    AssignmentCandidate candidate1 = candidateWithNameEnergylevelInitialRoutingCosts("AA", 3, 60);
    AssignmentCandidate candidate2 = candidateWithNameEnergylevelInitialRoutingCosts("CC", 2, 60);
    AssignmentCandidate candidate3 = candidateWithNameEnergylevelInitialRoutingCosts("BB", 1, 20);
    AssignmentCandidate candidate4 = candidateWithNameEnergylevelInitialRoutingCosts("DD", 1, 20);

    List<AssignmentCandidate> list = new ArrayList<>();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);
    list.add(candidate4);

    Collections.sort(list, comparator);

    assertThat(list.get(0), is(theInstance(candidate3)));
    assertThat(list.get(1), is(theInstance(candidate4)));
    assertThat(list.get(2), is(theInstance(candidate1)));
    assertThat(list.get(3), is(theInstance(candidate2)));
  }

  private AssignmentCandidate candidateWithName(String name) {
    TransportOrder trasportOrder = new TransportOrder("TOrder-1", new ArrayList<>());
    return new AssignmentCandidate(
        new Vehicle(name),
        trasportOrder,
        buildDriveOrders(10)
    );
  }

  private AssignmentCandidate candidateWithNameEnergylevel(
      String name,
      int energyLevel
  ) {
    TransportOrder trasportOrder = new TransportOrder("TOrder-1", new ArrayList<>());
    return new AssignmentCandidate(
        new Vehicle(name).withEnergyLevel(energyLevel),
        trasportOrder,
        buildDriveOrders(10)
    );
  }

  private AssignmentCandidate candidateWithNameEnergylevelInitialRoutingCosts(
      String name,
      int energyLevel,
      long routingCost
  ) {
    TransportOrder trasportOrder = new TransportOrder("TOrder-1", new ArrayList<>());
    return new AssignmentCandidate(
        new Vehicle(name).withEnergyLevel(energyLevel),
        trasportOrder,
        buildDriveOrders(routingCost)
    );
  }

  private List<DriveOrder> buildDriveOrders(long routingCost) {
    Route.Step dummyStep
        = new Route.Step(
            null,
            new Point("Point1"),
            new Point("Point2"),
            Vehicle.Orientation.FORWARD,
            1,
            routingCost
        );
    Route route = new Route(Arrays.asList(dummyStep));
    List<DriveOrder> driveOrders = List.of(
        new DriveOrder("order1", new DriveOrder.Destination(new Point("Point2").getReference()))
            .withRoute(route)
    );
    return driveOrders;
  }

}
