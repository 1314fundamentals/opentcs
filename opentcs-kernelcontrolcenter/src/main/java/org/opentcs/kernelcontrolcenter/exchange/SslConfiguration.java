// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernelcontrolcenter.exchange;

import org.opentcs.configuration.ConfigurationEntry;
import org.opentcs.configuration.ConfigurationPrefix;

/**
 * Provides methods to configure the ssl connection.
 */
@ConfigurationPrefix(SslConfiguration.PREFIX)
public interface SslConfiguration {

  /**
   * This configuration's prefix.
   */
  String PREFIX = "ssl";

  @ConfigurationEntry(
      type = "Boolean",
      description = "Whether to use SSL to encrypt RMI connections to the kernel.",
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_0"
  )
  boolean enable();

  @ConfigurationEntry(
      type = "String",
      description = "The path to the SSL truststore.",
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_1"
  )
  String truststoreFile();

  @ConfigurationEntry(
      type = "String",
      description = "The password for the SSL truststore.",
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_2"
  )
  String truststorePassword();
}
