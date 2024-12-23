// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.modeleditor.components.drawing;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import jakarta.inject.Singleton;
import org.jhotdraw.draw.DrawingEditor;
import org.opentcs.guing.common.components.drawing.DrawingOptions;
import org.opentcs.guing.common.components.drawing.OpenTCSDrawingEditor;
import org.opentcs.guing.common.components.drawing.OpenTCSDrawingView;
import org.opentcs.guing.common.components.drawing.figures.FigureFactory;
import org.opentcs.thirdparty.modeleditor.jhotdraw.components.drawing.OpenTCSDrawingViewModeling;

/**
 * A Guice module for this package.
 */
public class DrawingInjectionModule
    extends
      AbstractModule {

  /**
   * Creates a new instance.
   */
  public DrawingInjectionModule() {
  }

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(FigureFactory.class));

    bind(OpenTCSDrawingEditor.class).in(Singleton.class);
    bind(DrawingEditor.class).to(OpenTCSDrawingEditor.class);

    bind(OpenTCSDrawingView.class).to(OpenTCSDrawingViewModeling.class);

    bind(DrawingOptions.class).in(Singleton.class);
  }
}
