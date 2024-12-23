// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentcs.access.KernelRuntimeException;
import org.opentcs.data.ObjectExistsException;
import org.opentcs.data.ObjectUnknownException;

/**
 * Tests for {@link KernelExecutorWrapper}.
 */
class KernelExecutorWrapperTest {

  private ExecutorService executorService;
  private KernelExecutorWrapper executorWrapper;

  @BeforeEach
  void setUp() {
    this.executorService = Executors.newSingleThreadExecutor();
    this.executorWrapper = new KernelExecutorWrapper(executorService);
  }

  @AfterEach
  void tearDown() {
    executorService.shutdown();
  }

  @Test
  void returnValueReturnedInCallable() {
    assertThat(
        executorWrapper.callAndWait(() -> "my result"),
        is("my result")
    );
  }

  @Test
  void forwardCausingExceptionIfRuntimeException() {
    assertThrows(
        ObjectUnknownException.class,
        () -> {
          executorWrapper.callAndWait(() -> {
            throw new ObjectUnknownException("some exception");
          });
        }
    );

    assertThrows(
        ObjectExistsException.class,
        () -> {
          executorWrapper.callAndWait(() -> {
            throw new ObjectExistsException("some exception");
          });
        }
    );
  }

  @Test
  void wrapUnhandledExceptionInKernelRuntimeException() {
    assertThrows(
        KernelRuntimeException.class,
        () -> {
          executorWrapper.callAndWait(() -> {
            throw new Exception("some exception");
          });
        }
    );

  }
}
