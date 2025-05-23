// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.plugins.panels.allocation;

import static java.util.Objects.requireNonNull;

import java.awt.Component;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.opentcs.data.model.Path;
import org.opentcs.data.model.Point;
import org.opentcs.data.model.TCSResourceReference;

/**
 * Renders the tree nodes with vehicle, point and path icons.
 */
public class AllocationTreeCellRenderer
    extends
      DefaultTreeCellRenderer {

  /**
   * The icon for vehicles in the tree view.
   */
  private final ImageIcon vehicleIcon;
  /**
   * The icon for points in the tree view.
   */
  private final ImageIcon pointIcon;
  /**
   * The icon for paths in the tree view.
   */
  private final ImageIcon pathIcon;

  /**
   * Creates a new instance.
   */
  public AllocationTreeCellRenderer() {
    vehicleIcon = iconByFullPath(
        "/org/opentcs/guing/plugins/panels/allocation/symbols/vehicle.18x18.png"
    );
    pointIcon = iconByFullPath(
        "/org/opentcs/guing/plugins/panels/allocation/symbols/point.18x18.png"
    );
    pathIcon = iconByFullPath(
        "/org/opentcs/guing/plugins/panels/allocation/symbols/path.18x18.png"
    );
  }

  @Override
  public Component getTreeCellRendererComponent(
      JTree tree, Object node, boolean selected,
      boolean expanded, boolean isLeaf, int row,
      boolean hasFocus
  ) {
    //Let the superclass handle all its stuff related to rendering
    super.getTreeCellRendererComponent(tree, node, selected, expanded, isLeaf, row, hasFocus);
    if (node instanceof DefaultMutableTreeNode) {
      DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
      //User object is of type string only if the node contains a vehicle or is the root node
      if (treeNode.getUserObject() instanceof String) {
        setIcon(vehicleIcon);
      }
      //User object is of type TCSResource only if the node contains a path or a point
      else if (treeNode.getUserObject() instanceof TCSResourceReference) {
        TCSResourceReference<?> resource = (TCSResourceReference<?>) treeNode.getUserObject();
        setText(resource.getName());
        if (resource.getReferentClass() == Path.class) {
          setIcon(pathIcon);
        }
        else if (resource.getReferentClass() == Point.class) {
          setIcon(pointIcon);
        }
      }
    }
    return this;
  }

  /**
   * Creates an ImageIcon.
   *
   * @param fullPath The full (absolute) path of the icon file.
   * @return The icon, or <code>null</code>, if the file does not exist.
   */
  private ImageIcon iconByFullPath(String fullPath) {
    requireNonNull(fullPath, "fullPath");

    URL url = getClass().getResource(fullPath);

    if (url == null) {
      throw new IllegalArgumentException("Icon file not found: " + fullPath);
    }
    return new ImageIcon(url);
  }

}
