// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.access;

import java.io.Serializable;

/**
 * A runtime exception thrown by the openTCS kernel.
 */
public class KernelRuntimeException
    extends
      RuntimeException
    implements
      Serializable {

  /**
   * Constructs a new instance with no detail message.
   */
  public KernelRuntimeException() {
    super();
  }

  /**
   * Constructs a new instance with the specified detail message.
   *
   * @param message The detail message.
   */
  public KernelRuntimeException(String message) {
    super(message);
  }

  /**
   * Constructs a new instance with the specified detail message and cause.
   *
   * @param message The detail message.
   * @param cause The exception's cause.
   */
  public KernelRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new instance with the specified cause and a detail
   * message of <code>(cause == null ? null : cause.toString())</code> (which
   * typically contains the class and detail message of <code>cause</code>).
   *
   * @param cause The exception's cause.
   */
  public KernelRuntimeException(Throwable cause) {
    super(cause);
  }
}
