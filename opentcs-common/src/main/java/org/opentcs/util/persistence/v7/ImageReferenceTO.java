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
@XmlType(propOrder = {"imageRef", "positionOffsetX", "positionOffsetY", "sizeX"})
public class ImageReferenceTO {

  private String imageRef = "";
  private Long positionOffsetX = 0L;
  private Long positionOffsetY = 0L;
  private Long sizeX = 0L;

  /**
   * Creates a new instance.
   */
  public ImageReferenceTO() {
  }

  @XmlAttribute(required = true)
  public String getImageRef() {
    return imageRef;
  }

  public ImageReferenceTO setImageRef(
      @Nonnull
      String imageRef
  ) {
    this.imageRef = requireNonNull(imageRef, "imageRef");
    return this;
  }

  @XmlAttribute(required = true)
  public Long getPositionOffsetX() {
    return positionOffsetX;
  }

  public ImageReferenceTO setPositionOffsetX(
      @Nonnull
      Long positionOffsetX
  ) {
    this.positionOffsetX = requireNonNull(positionOffsetX, "positionOffsetX");
    return this;
  }

  @XmlAttribute(required = true)
  public Long getPositionOffsetY() {
    return positionOffsetY;
  }

  public ImageReferenceTO setPositionOffsetY(
      @Nonnull
      Long positionOffsetY
  ) {
    this.positionOffsetY = requireNonNull(positionOffsetY, "positionOffsetY");
    return this;
  }

  @XmlAttribute(required = true)
  public Long getSizeX() {
    return sizeX;
  }

  public ImageReferenceTO setSizeX(
      @Nonnull
      Long sizeX
  ) {
    this.sizeX = requireNonNull(sizeX, "sizeX");
    return this;
  }
}
