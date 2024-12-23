// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.modeleditor.application.menus.menubar;

import static java.util.Objects.requireNonNull;

import jakarta.inject.Inject;
import java.util.Set;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.opentcs.components.plantoverview.PlantModelExporter;
import org.opentcs.guing.common.application.GuiManager;
import org.opentcs.modeleditor.application.action.file.ExportPlantModelAction;
import org.opentcs.modeleditor.util.I18nPlantOverviewModeling;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 */
public class FileExportMenu
    extends
      JMenu {

  private static final ResourceBundleUtil LABELS
      = ResourceBundleUtil.getBundle(I18nPlantOverviewModeling.MENU_PATH);

  @Inject
  @SuppressWarnings("this-escape")
  public FileExportMenu(
      Set<PlantModelExporter> exporters,
      GuiManager guiManager
  ) {
    super(LABELS.getString("fileExportMenu.text"));
    requireNonNull(exporters, "exporters");
    requireNonNull(guiManager, "guiManager");

    for (PlantModelExporter exporter : exporters) {
      add(new JMenuItem(new ExportPlantModelAction(exporter, guiManager)));
    }
  }
}
