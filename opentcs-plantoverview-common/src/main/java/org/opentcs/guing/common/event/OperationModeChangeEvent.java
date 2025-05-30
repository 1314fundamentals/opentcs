// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.event;

import static java.util.Objects.requireNonNull;

import java.util.EventObject;
import org.opentcs.guing.common.application.OperationMode;

/**
 * Informs listeners about a change of the application's mode of operation.
 */
public class OperationModeChangeEvent
    extends
      EventObject {

  /**
   * The old operation mode.
   */
  private final OperationMode oldMode;
  /**
   * The new/current operation mode.
   */
  private final OperationMode newMode;

  /**
   * Creates a new instance.
   *
   * @param source The source of this event.
   * @param oldMode The old operation mode.
   * @param newMode The new/current operation mode.
   */
  public OperationModeChangeEvent(
      Object source,
      OperationMode oldMode,
      OperationMode newMode
  ) {
    super(source);
    this.oldMode = requireNonNull(oldMode, "oldMode");
    this.newMode = requireNonNull(newMode, "newMode");
  }

  /**
   * Returns the old operation mode.
   *
   * @return The old operation mode.
   */
  public OperationMode getOldMode() {
    return oldMode;
  }

  /**
   * Returns the new/current operation mode.
   *
   * @return The new/current operation mode.
   */
  public OperationMode getNewMode() {
    return newMode;
  }

  @Override
  public String toString() {
    return "OperationModeChangeEvent{"
        + "oldMode=" + oldMode
        + ", newMode=" + newMode
        + ", source=" + getSource()
        + '}';
  }
}
