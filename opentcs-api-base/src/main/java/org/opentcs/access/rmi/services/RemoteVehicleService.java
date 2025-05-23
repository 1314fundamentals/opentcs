// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.access.rmi.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;
import org.opentcs.access.rmi.ClientID;
import org.opentcs.components.kernel.services.VehicleService;
import org.opentcs.data.TCSObjectReference;
import org.opentcs.data.model.AcceptableOrderType;
import org.opentcs.data.model.Vehicle;
import org.opentcs.data.model.Vehicle.EnergyLevelThresholdSet;
import org.opentcs.drivers.vehicle.AdapterCommand;
import org.opentcs.drivers.vehicle.VehicleCommAdapterDescription;
import org.opentcs.drivers.vehicle.VehicleCommAdapterMessage;
import org.opentcs.drivers.vehicle.management.VehicleAttachmentInformation;
import org.opentcs.drivers.vehicle.management.VehicleProcessModelTO;
import org.opentcs.util.annotations.ScheduledApiChange;

/**
 * Declares the methods provided by the {@link VehicleService} via RMI.
 *
 * <p>
 * The majority of the methods declared here have signatures analogous to their counterparts in
 * {@link VehicleService}, with an additional {@link ClientID} parameter which serves the purpose
 * of identifying the calling client and determining its permissions.
 * </p>
 * <p>
 * To avoid redundancy, the semantics of methods that only pass through their arguments are not
 * explicitly documented here again. See the corresponding API documentation in
 * {@link VehicleService} for these, instead.
 * </p>
 */
public interface RemoteVehicleService
    extends
      RemoteTCSObjectService,
      Remote {

  // CHECKSTYLE:OFF
  void attachCommAdapter(
      ClientID clientId, TCSObjectReference<Vehicle> ref,
      VehicleCommAdapterDescription description
  )
      throws RemoteException;

  void disableCommAdapter(ClientID clientId, TCSObjectReference<Vehicle> ref)
      throws RemoteException;

  void enableCommAdapter(ClientID clientId, TCSObjectReference<Vehicle> ref)
      throws RemoteException;

  VehicleAttachmentInformation fetchAttachmentInformation(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref
  )
      throws RemoteException;

  VehicleProcessModelTO fetchProcessModel(ClientID clientId, TCSObjectReference<Vehicle> ref)
      throws RemoteException;

  @Deprecated
  void sendCommAdapterCommand(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      AdapterCommand command
  )
      throws RemoteException;

  @Deprecated
  void sendCommAdapterMessage(
      ClientID clientId,
      TCSObjectReference<Vehicle> vehicleRef,
      Object message
  )
      throws RemoteException;

  default void sendCommAdapterMessage(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      VehicleCommAdapterMessage message
  )
      throws RemoteException {
  }

  void updateVehicleIntegrationLevel(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      Vehicle.IntegrationLevel integrationLevel
  )
      throws RemoteException;

  void updateVehiclePaused(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      boolean paused
  )
      throws RemoteException;

  @ScheduledApiChange(when = "7.0", details = "Default implementation will be removed.")
  default void updateVehicleEnergyLevelThresholdSet(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      EnergyLevelThresholdSet energyLevelThresholdSet
  )
      throws RemoteException {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  @Deprecated
  @ScheduledApiChange(when = "7.0", details = "Will be removed.")
  void updateVehicleAllowedOrderTypes(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      Set<String> allowedOrderTypes
  )
      throws RemoteException;

  default void updateVehicleAcceptableOrderTypes(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      Set<AcceptableOrderType> acceptableOrderTypes
  )
      throws RemoteException {
    throw new UnsupportedOperationException("Not yet implemented.");
  }

  void updateVehicleEnvelopeKey(
      ClientID clientId,
      TCSObjectReference<Vehicle> ref,
      String envelopeKey
  )
      throws RemoteException;
  // CHECKSTYLE:ON
}
