// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties.panel;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import org.opentcs.guing.common.components.dialogs.StandardDetailsDialog;
import org.opentcs.guing.common.util.I18nPlantOverview;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * Allows editing of actions that a vehicle can execute at stations with the location type.
 */
public class LocationTypeActionsEditorPanel
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
  public LocationTypeActionsEditorPanel() {
  }

  @Override
  public String getTitle() {
    return bundle.getString("locationTypeActionsEditorPanel.title");
  }

  @Override
  protected void edit() {
    String value = getItemsList().getSelectedValue();

    if (value == null) {
      return;
    }

    int index = getItemsList().getSelectedIndex();
    JDialog parent = (JDialog) getTopLevelAncestor();
    StringPanel content = new StringPanel(
        bundle.getString("locationTypeActionsEditorPanel.dialog_actionDefinitionEdit.title"),
        bundle.getString(
            "locationTypeActionsEditorPanel.dialog_actionDefinition.label_action.text"
        ),
        value
    );
    StandardDetailsDialog dialog = new StandardDetailsDialog(parent, true, content);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);

    if (dialog.getReturnStatus() == StandardDetailsDialog.RET_OK) {
      DefaultListModel<String> model = (DefaultListModel<String>) getItemsList().getModel();
      model.setElementAt(content.getText(), index);
    }
  }

  @Override
  protected void add() {
    JDialog parent = (JDialog) getTopLevelAncestor();
    StringPanel content = new StringPanel(
        bundle.getString("locationTypeActionsEditorPanel.dialog_actionDefinitionAdd.title"),
        bundle.getString(
            "locationTypeActionsEditorPanel.dialog_actionDefinition.label_action.text"
        ),
        ""
    );
    StandardDetailsDialog dialog = new StandardDetailsDialog(parent, true, content);
    dialog.setLocationRelativeTo(parent);
    dialog.setVisible(true);

    if (dialog.getReturnStatus() == StandardDetailsDialog.RET_OK) {
      DefaultListModel<String> model = (DefaultListModel<String>) getItemsList().getModel();
      model.addElement(content.getText());
    }
  }
}
