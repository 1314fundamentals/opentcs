// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v8.binding.response;

import java.time.Instant;
import java.util.List;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentcs.kernel.extensions.servicewebapi.common.JsonBinder;
import org.opentcs.kernel.extensions.servicewebapi.v8.auth.UserPermission;

/**
 * Tests for {@link LoginResponseTO}.
 */
class LoginResponseTOTest {

  private JsonBinder jsonBinder;

  @BeforeEach
  void setUp() {
    jsonBinder = new JsonBinder();
  }

  @Test
  void jsonSample() {
    Approvals.verify(jsonBinder.toJson(createLoginResponseTO()));
  }

  private LoginResponseTO createLoginResponseTO() {
    return new LoginResponseTO()
        .setUser(
            new LoginResponseTO.User()
                .setUsername("some-username")
                .setPermissions(
                    List.of(
                        UserPermission.READ_DATA,
                        UserPermission.MODIFY_ORDER
                    )
                )
        )
        .setSession(
            new LoginResponseTO.Session()
                .setCreationTime(Instant.EPOCH)
                .setExpirationTime(Instant.parse("2026-08-24T14:50:00Z"))
        );
  }
}
