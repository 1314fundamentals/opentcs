// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.drawing.figures.liner;

import java.util.Collection;
import java.util.Collections;
import org.jhotdraw.draw.ConnectionFigure;
import org.jhotdraw.draw.LineConnectionFigure;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.liner.Liner;
import org.jhotdraw.geom.BezierPath;

/**
 * A {@link Liner} that constrains a connection to a fourth-order curved
 * line.
 */
public class TripleBezierLiner
    implements
      org.jhotdraw.draw.liner.Liner {

  /**
   * Creates a new instance.
   */
  public TripleBezierLiner() {
  }

  @Override  // Liner
  public Collection<Handle> createHandles(BezierPath path) {
    return Collections.emptyList();
  }

  @Override  // Liner
  public void lineout(ConnectionFigure figure) {
    BezierPath path = ((LineConnectionFigure) figure).getBezierPath();

    if (path != null) {
      path.invalidatePath();
    }
  }

  @Override // Object
  public Liner clone() {
    try {
      return (Liner) super.clone();
    }
    catch (CloneNotSupportedException ex) {
      InternalError error = new InternalError(ex.getMessage());
      error.initCause(ex);
      throw error;
    }
  }
}
