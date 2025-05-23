// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.application.action.course;

import static java.util.Objects.requireNonNull;
import static org.opentcs.operationsdesk.util.I18nPlantOverviewOperating.VEHICLEPOPUP_PATH;

import com.google.inject.assistedinject.Assisted;
import jakarta.inject.Inject;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.jhotdraw.draw.Figure;
import org.opentcs.guing.base.model.elements.VehicleModel;
import org.opentcs.guing.common.components.drawing.OpenTCSDrawingEditor;
import org.opentcs.guing.common.components.drawing.OpenTCSDrawingView;
import org.opentcs.guing.common.persistence.ModelManager;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 */
public class ScrollToVehicleAction
    extends
      AbstractAction {

  /**
   * Scrolls to a vehicle in the drawing.
   */
  public static final String ID = "course.vehicle.scrollTo";

  private static final ResourceBundleUtil BUNDLE = ResourceBundleUtil.getBundle(VEHICLEPOPUP_PATH);
  /**
   * The vehicle.
   */
  private final VehicleModel vehicleModel;
  /**
   * The drawing editor.
   */
  private final OpenTCSDrawingEditor drawingEditor;
  /**
   * The model manager.
   */
  private final ModelManager modelManager;

  /**
   * Creates a new instance.
   *
   * @param vehicle The selected vehicle.
   * @param drawingEditor The application's drawing editor.
   * @param modelManager The model manager.
   */
  @Inject
  @SuppressWarnings("this-escape")
  public ScrollToVehicleAction(
      @Assisted
      VehicleModel vehicle,
      OpenTCSDrawingEditor drawingEditor,
      ModelManager modelManager
  ) {
    this.vehicleModel = requireNonNull(vehicle, "vehicle");
    this.drawingEditor = requireNonNull(drawingEditor, "drawingEditor");
    this.modelManager = requireNonNull(modelManager, "modelManager");

    putValue(NAME, BUNDLE.getString("scrollToVehicleAction.name"));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Figure figure = modelManager.getModel().getFigure(vehicleModel);
    OpenTCSDrawingView drawingView = drawingEditor.getActiveView();

    if (drawingView != null && figure != null) {
      drawingView.clearSelection();
      drawingView.addToSelection(figure);
      drawingView.scrollTo(figure);
    }
  }
}
