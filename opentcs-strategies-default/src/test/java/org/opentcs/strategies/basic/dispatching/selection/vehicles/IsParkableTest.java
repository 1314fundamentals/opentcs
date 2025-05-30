// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.selection.vehicles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.opentcs.components.kernel.services.TCSObjectService;
import org.opentcs.data.model.AcceptableOrderType;
import org.opentcs.data.model.Point;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.order.OrderConstants;
import org.opentcs.data.order.OrderSequence;
import org.opentcs.strategies.basic.dispatching.DefaultDispatcherConfiguration;
import org.opentcs.util.TimeProvider;

/**
 * Test for {@link IsParkable}.
 */
class IsParkableTest {

  private TCSObjectService objectService;
  private DefaultDispatcherConfiguration configuration;
  private TimeProvider timeProvider;
  private IsParkable isParkable;
  private Vehicle parkableVehicle;
  private Point p1;

  @BeforeEach
  void setUp() {
    objectService = mock();
    configuration = mock();
    timeProvider = mock();
    isParkable = new IsParkable(objectService, configuration, timeProvider);
    p1 = new Point("p1");
    parkableVehicle = new Vehicle("V1")
        .withIntegrationLevel(Vehicle.IntegrationLevel.TO_BE_UTILIZED)
        .withState(Vehicle.State.IDLE)
        .withProcState(Vehicle.ProcState.IDLE)
        .withCurrentPosition(p1.getReference())
        .withAcceptableOrderTypes(
            Set.of(new AcceptableOrderType(OrderConstants.TYPE_ANY, 0))
        );

    given(objectService.fetchObject(Point.class, p1.getReference()))
        .willReturn(p1);
    long parkIdleVehiclesDelay = 60000;
    given(configuration.parkIdleVehiclesDelay()).willReturn(parkIdleVehiclesDelay);
    given(timeProvider.getCurrentTimeInstant())
        .willReturn(parkableVehicle.getProcStateTimestamp().plusMillis(parkIdleVehiclesDelay + 1));
  }

  @ParameterizedTest
  @ValueSource(strings = {OrderConstants.TYPE_ANY, OrderConstants.TYPE_PARK})
  void checkVehicleIsParkable(String type) {
    Vehicle vehicle = parkableVehicle
        .withAcceptableOrderTypes(Set.of(new AcceptableOrderType(type, 0)));
    assertThat(isParkable.apply(vehicle), hasSize(0));
  }

  @ParameterizedTest
  @EnumSource(
      value = Vehicle.IntegrationLevel.class,
      names = {"TO_BE_IGNORED", "TO_BE_NOTICED", "TO_BE_RESPECTED"}
  )
  void checkVehicleIsNotFullyIntegrated(Vehicle.IntegrationLevel integrationLevel) {
    Vehicle vehicle = parkableVehicle.withIntegrationLevel(integrationLevel);

    assertThat(isParkable.apply(vehicle), hasSize(1));
  }

  @Test
  void checkVehiclePositionIsUnknown() {
    assertThat(isParkable.apply(parkableVehicle.withCurrentPosition(null)), hasSize(1));
  }

  @Test
  void checkVehicleHasParkingPosition() {
    p1 = p1.withType(Point.Type.PARK_POSITION);
    Vehicle vehicle = parkableVehicle.withCurrentPosition(p1.getReference());

    given(objectService.fetchObject(Point.class, p1.getReference()))
        .willReturn(p1);

    assertThat(isParkable.apply(vehicle), hasSize(1));
  }

  @ParameterizedTest
  @EnumSource(
      value = Vehicle.State.class,
      names = {"UNKNOWN", "UNAVAILABLE", "ERROR", "EXECUTING", "CHARGING"}
  )
  void checkVehicleHasIncorrectState(Vehicle.State state) {
    Vehicle vehicle = parkableVehicle.withState(state);

    assertThat(isParkable.apply(vehicle), hasSize(1));
  }

  @ParameterizedTest
  @EnumSource(
      value = Vehicle.ProcState.class,
      names = {"AWAITING_ORDER", "PROCESSING_ORDER"}
  )
  void checkVehicleHasIncorrectProcState(Vehicle.ProcState procState) {
    Vehicle vehicle = parkableVehicle.withProcState(procState);

    assertThat(isParkable.apply(vehicle), hasSize(1));
  }

  @Test
  void checkVehicleHasOrderSequence() {
    Vehicle vehicle = parkableVehicle
        .withOrderSequence(new OrderSequence("OS").getReference());

    assertThat(isParkable.apply(vehicle), hasSize(1));
  }

  @Test
  void checkVehicleIsNotAllowedToPark() {
    Vehicle vehicle = parkableVehicle
        .withAcceptableOrderTypes(
            Set.of(new AcceptableOrderType(OrderConstants.TYPE_CHARGE, 0))
        );

    assertThat(isParkable.apply(vehicle), hasSize(1));
  }

  @Test
  void checkVehicleIsNotIdleLongEnough() {
    given(timeProvider.getCurrentTimeInstant())
        .willReturn(parkableVehicle.getProcStateTimestamp().plusMillis(30000));
    assertThat(isParkable.apply(parkableVehicle), hasSize(1));
  }
}
