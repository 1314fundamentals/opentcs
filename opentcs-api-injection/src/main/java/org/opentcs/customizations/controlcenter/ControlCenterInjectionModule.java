// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.customizations.controlcenter;

import com.google.inject.multibindings.Multibinder;
import org.opentcs.components.kernelcontrolcenter.ControlCenterPanel;
import org.opentcs.customizations.ConfigurableInjectionModule;
import org.opentcs.drivers.peripherals.management.PeripheralCommAdapterPanelFactory;
import org.opentcs.drivers.vehicle.management.VehicleCommAdapterPanelFactory;

/**
 * A base class for Guice modules adding or customizing bindings for the kernel control center
 * application.
 */
public abstract class ControlCenterInjectionModule
    extends
      ConfigurableInjectionModule {

  /**
   * Returns a multibinder that can be used to register {@link ControlCenterPanel} implementations
   * for the kernel's modelling mode.
   *
   * @return The multibinder.
   */
  protected Multibinder<ControlCenterPanel> controlCenterPanelBinderModelling() {
    return Multibinder.newSetBinder(
        binder(),
        ControlCenterPanel.class,
        ActiveInModellingMode.class
    );
  }

  /**
   * Returns a multibinder that can be used to register {@link ControlCenterPanel} implementations
   * for the kernel's operating mode.
   *
   * @return The multibinder.
   */
  protected Multibinder<ControlCenterPanel> controlCenterPanelBinderOperating() {
    return Multibinder.newSetBinder(
        binder(),
        ControlCenterPanel.class,
        ActiveInOperatingMode.class
    );
  }

  /**
   * Returns a multibinder that can be used to register {@link VehicleCommAdapterPanelFactory}
   * implementations.
   *
   * @return The multibinder.
   */
  protected Multibinder<VehicleCommAdapterPanelFactory> commAdapterPanelFactoryBinder() {
    return Multibinder.newSetBinder(binder(), VehicleCommAdapterPanelFactory.class);
  }

  /**
   * Returns a multibinder that can be used to register {@link PeripheralCommAdapterPanelFactory}
   * implementations.
   *
   * @return The multibinder.
   */
  protected Multibinder<PeripheralCommAdapterPanelFactory>
      peripheralCommAdapterPanelFactoryBinder() {
    return Multibinder.newSetBinder(binder(), PeripheralCommAdapterPanelFactory.class);
  }
}
