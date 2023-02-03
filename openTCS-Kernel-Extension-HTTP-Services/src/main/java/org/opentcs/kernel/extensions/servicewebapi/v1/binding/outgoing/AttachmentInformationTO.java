/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.kernel.extensions.servicewebapi.v1.binding.outgoing;

import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;
import org.opentcs.drivers.vehicle.management.AttachmentInformation;

/**
 * Arranges the data from a vehicle's <code>AttachmentInformation</code> for transferring.
 *
 * @author Sebastian Bonna (Fraunhofer IML)
 */
public class AttachmentInformationTO {

  /**
   * The vehicle this attachment information belongs to.
   */
  private String vehicleName;
  /**
   * The list of comm adapters available to be attached to the referenced vehicle.
   */
  private List<String> availableCommAdapters;
  /**
   * The comm adapter attached to the referenced vehicle.
   */
  private String attachedCommAdapter;

  private AttachmentInformationTO() {
  }

  public void setVehicleName(String vehicleName) {
    this.vehicleName = requireNonNull(vehicleName, "vehicleName");
  }

  public String getVehicleName() {
    return vehicleName;
  }

  public void setAvailableCommAdapters(List<String> availableCommAdapters) {
    this.availableCommAdapters = requireNonNull(availableCommAdapters, "availableCommAdapters");
  }

  public List<String> getAvailableCommAdapters() {
    return availableCommAdapters;
  }

  public void setAttachedCommAdapter(String attachedCommAdapter) {
    this.attachedCommAdapter = requireNonNull(attachedCommAdapter, "attachedCommAdapter");
  }

  public String getAttachedCommAdapter() {
    return attachedCommAdapter;
  }

  /**
   * Creates a new instance from <code>AttachmentInformation</code>.
   *
   * @param attachmentInformation The <code>AttachmentInformation</code> to create an instance from.
   * @return A new instance containing the data from the given <code>AttachmentInformation</code>.
   */
  public static AttachmentInformationTO fromAttachmentInformation(
      AttachmentInformation attachmentInformation) {
    if (attachmentInformation == null) {
      return null;
    }
    AttachmentInformationTO attachmentInformationTO = new AttachmentInformationTO();

    attachmentInformationTO.setVehicleName(
        attachmentInformation.getVehicleReference()
            .getName()
    );
    attachmentInformationTO.setAvailableCommAdapters(
        attachmentInformation.getAvailableCommAdapters()
            .stream()
            .map(description -> description.getClass().getName())
            .collect(Collectors.toList())
    );
    attachmentInformationTO.setAttachedCommAdapter(
        attachmentInformation.getAttachedCommAdapter()
            .getClass()
            .getName()
    );

    return attachmentInformationTO;
  }
}
