// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties.panel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import org.opentcs.guing.base.model.elements.LinkModel;
import org.opentcs.guing.base.model.elements.LocationTypeModel;
import org.opentcs.guing.common.components.dialogs.StandardDetailsDialog;
import org.opentcs.guing.common.util.I18nPlantOverview;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * Allows editing of actions that a vehicle can execute at a station.
 * Which actions are possible is determined by the station type.
 */
public class LinkActionsEditorPanel
    extends
      StringSetPropertyEditorPanel {

  /**
   * The bundle to be used.
   */
  private final ResourceBundleUtil bundle
      = ResourceBundleUtil.getBundle(I18nPlantOverview.PROPERTIES_PATH);

  /**
   * Creates a new instance.
   */
  public LinkActionsEditorPanel() {
  }

  @Override
  public String getTitle() {
    return bundle.getString("linkActionsEditorPanel.title");
  }

  @Override
  protected void edit() {
    String value = getItemsList().getSelectedValue();

    if (value == null) {
      return;
    }

    int index = getItemsList().getSelectedIndex();
    JDialog parent = (JDialog) getTopLevelAncestor();
    SelectionPanel content = new SelectionPanel(
        bundle.getString("linkActionsEditorPanel.dialog_actionSelectionEdit.title"),
        bundle.getString("linkActionsEditorPanel.dialog_actionSelection.label_action.text"),
        getPossibleItems(),
        value
    );
    StandardDetailsDialog dialog = new StandardDetailsDialog(parent, true, content);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);

    if (dialog.getReturnStatus() == StandardDetailsDialog.RET_OK) {
      DefaultListModel<String> model = (DefaultListModel<String>) getItemsList().getModel();
      model.setElementAt(content.getValue().toString(), index);
    }
  }

  @Override
  protected void add() {
    JDialog parent = (JDialog) getTopLevelAncestor();
    SelectionPanel content = new SelectionPanel(
        bundle.getString("linkActionsEditorPanel.dialog_actionSelectionAdd.title"),
        bundle.getString("linkActionsEditorPanel.dialog_actionSelection.label_action.text"),
        getPossibleItems()
    );
    StandardDetailsDialog dialog = new StandardDetailsDialog(parent, true, content);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);

    if (dialog.getReturnStatus() == StandardDetailsDialog.RET_OK) {
      DefaultListModel<String> model = (DefaultListModel<String>) getItemsList().getModel();
      Object value = content.getValue();
      if (value != null) {
        model.addElement(value.toString());
      }
    }
  }

  /**
   * Returns the possible actions that can be executed at a station.
   * The actions are determined by the station type.
   *
   * @return The possible actions.
   */
  private List<String> getPossibleItems() {
    LinkModel ref = (LinkModel) getProperty().getModel();
    LocationTypeModel type = ref.getLocation().getLocationType();

    return new ArrayList<>(type.getPropertyAllowedOperations().getItems());
  }
}
