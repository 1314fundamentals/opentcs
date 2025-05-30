// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.access.rmi.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.function.Predicate;
import org.opentcs.access.CredentialsException;
import org.opentcs.access.Kernel;
import org.opentcs.access.KernelServicePortal;
import org.opentcs.access.rmi.ClientID;

/**
 * Declares the methods provided by the {@link KernelServicePortal} via RMI.
 */
public interface RemoteKernelServicePortal
    extends
      Remote {

  /**
   * Introduce the calling client to the server and authenticate for operations.
   *
   * @param userName The user's name.
   * @param password The user's password.
   * @param eventFilter The event filter to be applied to events on the server side.
   * @return An identification object that is required for subsequent method calls.
   * @throws CredentialsException If authentication with the given username and password failed.
   * @throws RemoteException If there was an RMI-related problem.
   */
  ClientID login(String userName, String password, Predicate<Object> eventFilter)
      throws CredentialsException,
        RemoteException;

  void logout(ClientID clientId)
      throws RemoteException;

  Kernel.State getState(ClientID clientId)
      throws RemoteException;

  List<Object> fetchEvents(ClientID clientId, long timeout)
      throws RemoteException;

  void publishEvent(ClientID clientId, Object event)
      throws RemoteException;
}
