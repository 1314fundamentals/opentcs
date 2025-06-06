// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.selection.orders;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.opentcs.components.kernel.services.TCSObjectService;
import org.opentcs.data.TCSObject;
import org.opentcs.data.TCSObjectReference;
import org.opentcs.data.model.Location;
import org.opentcs.data.model.LocationType;
import org.opentcs.data.model.Point;
import org.opentcs.data.order.DriveOrder;
import org.opentcs.data.order.TransportOrder;

/**
 * Defines test cases for {@link ContainsLockedTargetLocations}.
 */
class ContainsLockedTargetLocationsTest {

  /**
   * The class to test.
   */
  private ContainsLockedTargetLocations filter;
  /**
   * The object service to use.
   */
  private TCSObjectService objectService;
  /**
   * The local object pool to be used by the object service.
   */
  private Map<TCSObjectReference<?>, TCSObject<?>> localObjectPool;

  @BeforeEach
  void setUp() {
    localObjectPool = new HashMap<>();
    objectService = mock(TCSObjectService.class);
    when(objectService.fetchObject(any(), ArgumentMatchers.<TCSObjectReference<?>>any()))
        .thenAnswer(
            invocation -> localObjectPool.get((TCSObjectReference<?>) invocation.getArgument(1))
        );
    filter = new ContainsLockedTargetLocations(objectService);
  }

  @Test
  void shouldFilterTransportOrderWithLockedLocation() {
    Collection<String> result = filter.apply(transportOrderWithLockedLocation());
    assertFalse(result.isEmpty());
  }

  @Test
  void shouldIgnoreTransportOrderWithUnlockedLocation() {
    Collection<String> result = filter.apply(transportOrderWithoutLockedLocation());
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldIgnoreTransportOrderWithPointDestination() {
    Collection<String> result = filter.apply(transportOrderWithPointDestination());
    assertTrue(result.isEmpty());
  }

  private TransportOrder transportOrderWithLockedLocation() {
    List<DriveOrder> driveOrders = new ArrayList<>();
    LocationType locationType = new LocationType("LocationType-1");

    Location location = new Location("Location-1", locationType.getReference());
    localObjectPool.put(location.getReference(), location);
    DriveOrder.Destination destination = new DriveOrder.Destination(location.getReference());
    driveOrders.add(new DriveOrder("order1", destination));

    location = new Location("Location-2", locationType.getReference()).withLocked(true);
    localObjectPool.put(location.getReference(), location);
    destination = new DriveOrder.Destination(location.getReference());
    driveOrders.add(new DriveOrder("order2", destination));

    return new TransportOrder("TransportOrder-1", driveOrders);
  }

  private TransportOrder transportOrderWithoutLockedLocation() {
    List<DriveOrder> driveOrders = new ArrayList<>();
    LocationType locationType = new LocationType("LocationType-1");

    Location location = new Location("Location-1", locationType.getReference());
    localObjectPool.put(location.getReference(), location);
    DriveOrder.Destination destination = new DriveOrder.Destination(location.getReference());
    driveOrders.add(new DriveOrder("order1", destination));

    location = new Location("Location-2", locationType.getReference());
    localObjectPool.put(location.getReference(), location);
    destination = new DriveOrder.Destination(location.getReference());
    driveOrders.add(new DriveOrder("order2", destination));

    return new TransportOrder("TransportOrder-1", driveOrders);
  }

  private TransportOrder transportOrderWithPointDestination() {
    List<DriveOrder> driveOrders = new ArrayList<>();

    Point point = new Point("Point-1");
    localObjectPool.put(point.getReference(), point);
    DriveOrder.Destination destination = new DriveOrder.Destination(point.getReference());
    driveOrders.add(new DriveOrder("order1", destination));

    return new TransportOrder("TransportOrder-1", driveOrders);
  }
}
