// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.operationsdesk.application.action.course;

import static java.util.Objects.requireNonNull;
import static org.opentcs.operationsdesk.util.I18nPlantOverviewOperating.VEHICLEPOPUP_PATH;

import com.google.inject.assistedinject.Assisted;
import jakarta.inject.Inject;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import org.opentcs.access.KernelRuntimeException;
import org.opentcs.access.SharedKernelServicePortal;
import org.opentcs.access.SharedKernelServicePortalProvider;
import org.opentcs.customizations.plantoverview.ApplicationFrame;
import org.opentcs.data.order.ReroutingType;
import org.opentcs.guing.base.model.elements.VehicleModel;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An action for triggering a rerouting of a selected set of vehicles.
 */
public class RerouteAction
    extends
      AbstractAction {

  private static final ResourceBundleUtil BUNDLE = ResourceBundleUtil.getBundle(VEHICLEPOPUP_PATH);
  private static final Logger LOG = LoggerFactory.getLogger(RerouteAction.class);
  private final Collection<VehicleModel> vehicles;
  private final ReroutingType reroutingType;
  private final SharedKernelServicePortalProvider portalProvider;
  private final Component dialogParent;

  /**
   * Creates a new instance.
   *
   * @param vehicles The selected vehicles.
   * @param reroutingType The selected rerouting type.
   * @param portalProvider Provides access to a shared kernel service portal.
   * @param dialogParent The parent component for dialogs shown by this action.
   */
  @Inject
  @SuppressWarnings("this-escape")
  public RerouteAction(
      @Assisted
      Collection<VehicleModel> vehicles,
      @Assisted
      ReroutingType reroutingType,
      SharedKernelServicePortalProvider portalProvider,
      @ApplicationFrame
      Component dialogParent
  ) {
    this.vehicles = requireNonNull(vehicles, "vehicles");
    this.reroutingType = requireNonNull(reroutingType, "reroutingType");
    this.portalProvider = requireNonNull(portalProvider, "portalProvider");
    this.dialogParent = requireNonNull(dialogParent, "dialogParent");

    switch (reroutingType) {
      case REGULAR:
        putValue(NAME, BUNDLE.getString("rerouteAction.regularRerouting.name"));
        break;
      case FORCED:
        putValue(NAME, BUNDLE.getString("rerouteAction.forcedRerouting.name"));
        break;
      default:
        putValue(NAME, reroutingType.name());
    }
  }

  @Override
  public void actionPerformed(ActionEvent evt) {
    if (reroutingType == ReroutingType.FORCED) {
      int dialogResult = JOptionPane.showConfirmDialog(
          dialogParent,
          BUNDLE.getString("rerouteAction.optionPane_confirmForcedRerouting.message"),
          BUNDLE.getString("rerouteAction.optionPane_confirmForcedRerouting.title"),
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.WARNING_MESSAGE
      );

      if (dialogResult != JOptionPane.OK_OPTION) {
        return;
      }
    }

    try (SharedKernelServicePortal sharedPortal = portalProvider.register()) {
      for (VehicleModel vehicle : vehicles) {
        sharedPortal.getPortal().getDispatcherService().reroute(
            vehicle.getVehicle().getReference(),
            reroutingType
        );
      }
    }
    catch (KernelRuntimeException e) {
      LOG.warn("Unexpected exception", e);
    }
  }
}
