// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.phase.assignment.priorization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
import org.opentcs.strategies.basic.dispatching.priorization.candidate.CandidateComparatorDeadlineAtRiskFirst;
import org.opentcs.strategies.basic.dispatching.priorization.transportorder.TransportOrderComparatorDeadlineAtRiskFirst;

/**
 * Unit tests for {@link CandidateComparatorDeadlineAtRiskFirst}.
 */
class CandidateComparatorDeadlineAtRiskFirstTest {

  private CandidateComparatorDeadlineAtRiskFirst comparator;

  @BeforeEach
  void setUp() {
    DefaultDispatcherConfiguration configuration
        = Mockito.mock(DefaultDispatcherConfiguration.class);
    Mockito.when(configuration.deadlineAtRiskPeriod()).thenReturn(Long.valueOf(60 * 60 * 1000));

    this.comparator = new CandidateComparatorDeadlineAtRiskFirst(
        new TransportOrderComparatorDeadlineAtRiskFirst(configuration)
    );
  }

  @Test
  void sortCriticalDeadlinesUp() {
    Instant deadline = Instant.now();
    AssignmentCandidate candidate1 = candidateWithDeadline(deadline.plus(270, ChronoUnit.MINUTES));
    AssignmentCandidate candidate2 = candidateWithDeadline(deadline.plus(30, ChronoUnit.MINUTES));
    AssignmentCandidate candidate3 = candidateWithDeadline(deadline.plus(180, ChronoUnit.MINUTES));

    List<AssignmentCandidate> list = new ArrayList<>();
    list.add(candidate1);
    list.add(candidate2);
    list.add(candidate3);

    Collections.sort(list, comparator);

    assertThat(list.get(0), is(theInstance(candidate2)));
  }

  private AssignmentCandidate candidateWithDeadline(Instant time) {
    TransportOrder deadlinedOrder
        = new TransportOrder("Some order", new ArrayList<>()).withDeadline(time);
    Route.Step dummyStep = new Route.Step(
        null,
        new Point("Point1"),
        new Point("Point2"),
        Vehicle.Orientation.FORWARD,
        1,
        10
    );
    Route route = new Route(Arrays.asList(dummyStep));
    List<DriveOrder> driveOrders = Arrays.asList(
        new DriveOrder("order1", new DriveOrder.Destination(new Point("Point2").getReference()))
            .withRoute(route)
    );

    return new AssignmentCandidate(
        new Vehicle("Vehicle1"),
        deadlinedOrder,
        driveOrders
    );
  }
}
