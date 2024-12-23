// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel;

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
      type = "String",
      description = {"The file url of the keystore."},
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_0"
  )
  String keystoreFile();

  @ConfigurationEntry(
      type = "String",
      description = {"The password for the keystore."},
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_1"
  )
  String keystorePassword();

  @ConfigurationEntry(
      type = "String",
      description = {"The file url of the truststore."},
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_2"
  )
  String truststoreFile();

  @ConfigurationEntry(
      type = "String",
      description = {"The password for the truststore."},
      changesApplied = ConfigurationEntry.ChangesApplied.ON_APPLICATION_START,
      orderKey = "0_connection_3"
  )
  String truststorePassword();

}
