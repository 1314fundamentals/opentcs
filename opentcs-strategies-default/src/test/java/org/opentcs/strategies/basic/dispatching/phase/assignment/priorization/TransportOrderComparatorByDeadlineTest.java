// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.phase.assignment.priorization;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentcs.data.order.TransportOrder;
import org.opentcs.strategies.basic.dispatching.priorization.transportorder.TransportOrderComparatorByDeadline;

/**
 */
class TransportOrderComparatorByDeadlineTest {

  private TransportOrderComparatorByDeadline comparator;

  @BeforeEach
  void setUp() {
    comparator = new TransportOrderComparatorByDeadline();
  }

  @Test
  void sortEarlyDeadlinesUp() {
    Instant creationTime = Instant.now();
    TransportOrder plainOrder = new TransportOrder("Some order ", new ArrayList<>());
    TransportOrder order1 = plainOrder.withDeadline(creationTime);
    TransportOrder order2 = plainOrder.withDeadline(creationTime.plus(2, ChronoUnit.HOURS));
    TransportOrder order3 = plainOrder.withDeadline(creationTime.plus(1, ChronoUnit.HOURS));

    List<TransportOrder> list = new ArrayList<>();
    list.add(order1);
    list.add(order2);
    list.add(order3);

    Collections.sort(list, comparator);

    assertThat(list.get(0), is(theInstance(order1)));
    assertThat(list.get(1), is(theInstance(order3)));
    assertThat(list.get(2), is(theInstance(order2)));
  }

}
