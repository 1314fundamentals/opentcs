// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.rmi;

/**
 * Defines the possible permission flags of kernel clients.
 */
public enum UserPermission {

  /**
   * Indicates the client may retrieve any data from the kernel.
   */
  READ_DATA,
  /**
   * Indicates the client may change the kernel's state.
   */
  CHANGE_KERNEL_STATE,
  /**
   * Indicates the client may change the kernel's configuration items.
   */
  CHANGE_CONFIGURATION,
  /**
   * Indicates the client may load another model.
   */
  LOAD_MODEL,
  /**
   * Indicates the client may save the current model (under any name).
   */
  SAVE_MODEL,
  /**
   * Indicates the client may modify any data of the current model.
   */
  MODIFY_MODEL,
  /**
   * Indicates the client may add or remove temporary path locks.
   */
  LOCK_PATH,
  /**
   * Indicates the client may move/place vehicles and modify their states
   * explicitly.
   */
  MODIFY_VEHICLES,
  /**
   * Indicates the client may create/modify transport orders.
   */
  MODIFY_ORDER,
  /**
   * Indicates the client may modify peripheral states.
   */
  MODIFY_PERIPHERALS,
  /**
   * Indicates the client may create/modify peripheral jobs.
   */
  MODIFY_PERIPHERAL_JOBS,
  /**
   * Indicates the client may publish messages via the kernel.
   */
  PUBLISH_MESSAGES
}
