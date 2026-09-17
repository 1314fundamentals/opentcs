// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.util.persistence.v7;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

/**
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"id", "mediaType", "data"})
public class ImageTO {

  private String id = "";
  private String mediaType = "";
  private String data = "";

  /**
   * Creates a new instance.
   */
  public ImageTO() {
  }

  @XmlAttribute(required = true)
  public String getId() {
    return id;
  }

  public ImageTO setId(
      @Nonnull
      String id
  ) {
    this.id = requireNonNull(id, "id");
    return this;
  }

  @XmlAttribute(required = true)
  public String getMediaType() {
    return mediaType;
  }

  public ImageTO setMediaType(
      @Nonnull
      String mediaType
  ) {
    this.mediaType = requireNonNull(mediaType, "mediaType");
    return this;
  }

  @XmlAttribute(required = true)
  public String getData() {
    return data;
  }

  public ImageTO setData(
      @Nonnull
      String data
  ) {
    this.data = requireNonNull(data, "data");
    return this;
  }
}
