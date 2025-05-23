// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.components.layer;

import jakarta.inject.Singleton;
import org.opentcs.customizations.plantoverview.PlantOverviewInjectionModule;
import org.opentcs.guing.common.components.layer.DefaultLayerManager;
import org.opentcs.guing.common.components.layer.LayerEditor;
import org.opentcs.guing.common.components.layer.LayerGroupEditor;
import org.opentcs.guing.common.components.layer.LayerGroupManager;
import org.opentcs.guing.common.components.layer.LayerManager;

/**
 * A Guice module for this package.
 */
public class LayersInjectionModule
    extends
      PlantOverviewInjectionModule {

  /**
   * Creates a new instance.
   */
  public LayersInjectionModule() {
  }

  @Override
  protected void configure() {
    bind(DefaultLayerManager.class).in(Singleton.class);
    bind(LayerManager.class).to(DefaultLayerManager.class);
    bind(LayerEditor.class).to(DefaultLayerManager.class);
    bind(LayersPanel.class).in(Singleton.class);

    bind(LayerGroupManager.class).to(DefaultLayerManager.class);
    bind(LayerGroupEditor.class).to(DefaultLayerManager.class);
    bind(LayerGroupsPanel.class).in(Singleton.class);
  }
}
