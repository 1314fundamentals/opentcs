// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.util;

import static java.util.Objects.requireNonNull;

import jakarta.inject.Inject;
import org.opentcs.guing.base.model.elements.VehicleModel;
import org.opentcs.guing.common.util.CourseObjectFactory;
import org.opentcs.operationsdesk.components.drawing.figures.NamedVehicleFigure;
import org.opentcs.operationsdesk.components.drawing.figures.VehicleFigure;
import org.opentcs.operationsdesk.components.drawing.figures.VehicleFigureFactory;

/**
 * A factory for Figures and ModelComponents.
 */
public class VehicleCourseObjectFactory
    extends
      CourseObjectFactory {

  /**
   * A factory for figures.
   */
  private final VehicleFigureFactory figureFactory;

  /**
   * Creates a new instance.
   *
   * @param figureFactory A factory for figures.
   */
  @Inject
  public VehicleCourseObjectFactory(VehicleFigureFactory figureFactory) {
    super(figureFactory);
    this.figureFactory = requireNonNull(figureFactory, "figureFactory");
  }

  public VehicleFigure createVehicleFigure(VehicleModel model) {
    return figureFactory.createVehicleFigure(model);
  }

  public NamedVehicleFigure createNamedVehicleFigure(VehicleModel model) {
    return figureFactory.createNamedVehicleFigure(model);
  }
}
