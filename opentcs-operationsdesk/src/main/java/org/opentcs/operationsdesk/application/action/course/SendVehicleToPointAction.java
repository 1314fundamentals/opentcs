// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.application.action.course;

import static java.util.Objects.requireNonNull;
import static org.opentcs.operationsdesk.util.I18nPlantOverviewOperating.VEHICLEPOPUP_PATH;

import com.google.inject.assistedinject.Assisted;
import jakarta.inject.Inject;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import org.opentcs.customizations.plantoverview.ApplicationFrame;
import org.opentcs.guing.base.model.elements.PointModel;
import org.opentcs.guing.base.model.elements.VehicleModel;
import org.opentcs.guing.common.components.dialogs.StandardContentDialog;
import org.opentcs.guing.common.persistence.ModelManager;
import org.opentcs.operationsdesk.exchange.TransportOrderUtil;
import org.opentcs.operationsdesk.transport.PointPanel;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 */
public class SendVehicleToPointAction
    extends
      AbstractAction {

  /**
   * Sends a vehicle directly to a point.
   */
  public static final String ID = "course.vehicle.sendToPoint";

  private static final ResourceBundleUtil BUNDLE = ResourceBundleUtil.getBundle(VEHICLEPOPUP_PATH);
  /**
   * The vehicle.
   */
  private final VehicleModel vehicleModel;
  /**
   * The application's main frame.
   */
  private final JFrame applicationFrame;
  /**
   * Provides the current system model.
   */
  private final ModelManager modelManager;
  /**
   * A helper for creating transport orders with the kernel.
   */
  private final TransportOrderUtil orderUtil;

  /**
   * Creates a new instance.
   *
   * @param vehicle The selected vehicle.
   * @param applicationFrame The application's main view.
   * @param modelManager Provides the current system model.
   * @param orderUtil A helper for creating transport orders with the kernel.
   */
  @Inject
  @SuppressWarnings("this-escape")
  public SendVehicleToPointAction(
      @Assisted
      VehicleModel vehicle,
      @ApplicationFrame
      JFrame applicationFrame,
      ModelManager modelManager,
      TransportOrderUtil orderUtil
  ) {
    this.vehicleModel = requireNonNull(vehicle, "vehicle");
    this.applicationFrame = requireNonNull(applicationFrame, "applicationFrame");
    this.modelManager = requireNonNull(modelManager, "modelManager");
    this.orderUtil = requireNonNull(orderUtil, "orderUtil");

    putValue(NAME, BUNDLE.getString("sendVehicleToPointAction.name"));
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    List<PointModel> pointModels = pointModels();

    if (!pointModels.isEmpty()) {
      PointPanel contentPanel = new PointPanel(pointModels);
      StandardContentDialog fDialog = new StandardContentDialog(applicationFrame, contentPanel);
      contentPanel.addInputValidationListener(fDialog);
      fDialog.setTitle(evt.getActionCommand());
      fDialog.setVisible(true);

      if (fDialog.getReturnStatus() == StandardContentDialog.RET_OK) {
        PointModel point = (PointModel) contentPanel.getSelectedItem();
        orderUtil.createTransportOrder(point, vehicleModel);
      }
    }
  }

  private List<PointModel> pointModels() {
    return modelManager.getModel().getPointModels();
  }
}
