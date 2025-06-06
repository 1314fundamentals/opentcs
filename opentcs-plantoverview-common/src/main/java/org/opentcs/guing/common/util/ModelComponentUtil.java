// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.util;

import java.util.ArrayList;
import java.util.List;
import org.jhotdraw.draw.Figure;
import org.opentcs.guing.base.model.CompositeModelComponent;
import org.opentcs.guing.base.model.ModelComponent;
import org.opentcs.guing.common.model.SystemModel;

/**
 * Provides utility methods for {@link ModelComponent}s.
 */
public class ModelComponentUtil {

  /**
   * Prevent instantiation.
   */
  private ModelComponentUtil() {
  }

  public static List<Figure> getChildFigures(
      CompositeModelComponent parent,
      SystemModel systemModel
  ) {
    List<Figure> figures = new ArrayList<>();

    List<ModelComponent> childComps = parent.getChildComponents();
    synchronized (childComps) {
      for (ModelComponent component : childComps) {
        Figure figure = systemModel.getFigure(component);
        if (figure != null) {
          figures.add(figure);
        }
      }
    }

    return figures;
  }
}
