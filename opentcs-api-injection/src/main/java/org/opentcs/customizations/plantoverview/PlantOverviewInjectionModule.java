// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.customizations.plantoverview;

import com.google.inject.multibindings.Multibinder;
import org.opentcs.components.plantoverview.ObjectHistoryEntryFormatter;
import org.opentcs.components.plantoverview.OrderTypeSuggestions;
import org.opentcs.components.plantoverview.PlantModelExporter;
import org.opentcs.components.plantoverview.PlantModelImporter;
import org.opentcs.components.plantoverview.PluggablePanelFactory;
import org.opentcs.components.plantoverview.PropertySuggestions;
import org.opentcs.customizations.ConfigurableInjectionModule;

/**
 * A base class for Guice modules adding or customizing bindings for the plant overview application.
 */
public abstract class PlantOverviewInjectionModule
    extends
      ConfigurableInjectionModule {

  /**
   * Returns a multibinder that can be used to register plant model importers.
   *
   * @return The multibinder.
   */
  protected Multibinder<PlantModelImporter> plantModelImporterBinder() {
    return Multibinder.newSetBinder(binder(), PlantModelImporter.class);
  }

  /**
   * Returns a multibinder that can be used to register plant model exporters.
   *
   * @return The multibinder.
   */
  protected Multibinder<PlantModelExporter> plantModelExporterBinder() {
    return Multibinder.newSetBinder(binder(), PlantModelExporter.class);
  }

  /**
   * Returns a multibinder that can be used to register factories for pluggable panels.
   *
   * @return The multibinder.
   */
  protected Multibinder<PluggablePanelFactory> pluggablePanelFactoryBinder() {
    return Multibinder.newSetBinder(binder(), PluggablePanelFactory.class);
  }

  /**
   * Returns a multibinder that can be used to register classes that provide suggested properties.
   *
   * @return The multibinder.
   */
  protected Multibinder<PropertySuggestions> propertySuggestionsBinder() {
    return Multibinder.newSetBinder(binder(), PropertySuggestions.class);
  }

  /**
   * Returns a multibinder that can be used to register classes that provide suggested order types.
   *
   * @return The multibinder.
   */
  protected Multibinder<OrderTypeSuggestions> orderTypeSuggestionsBinder() {
    return Multibinder.newSetBinder(binder(), OrderTypeSuggestions.class);
  }

  /**
   * Returns a multibinder that can be used to register {@link ObjectHistoryEntryFormatter}s.
   *
   * @return The multibinder.
   */
  protected Multibinder<ObjectHistoryEntryFormatter> objectHistoryEntryFormatterBinder() {
    return Multibinder.newSetBinder(binder(), ObjectHistoryEntryFormatter.class);
  }
}
