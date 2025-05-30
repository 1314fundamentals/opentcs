// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.util.persistence.v005;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(
    propOrder = {"name", "xPosition", "yPosition", "zPosition", "links", "locked",
        "properties", "locationLayout"}
)
public class LocationTO
    extends
      PlantModelElementTO {

  private Long xPosition = 0L;
  private Long yPosition = 0L;
  private Long zPosition = 0L;
  private String type = "";
  private List<Link> links = new ArrayList<>();
  private Boolean locked = false;
  private LocationLayout locationLayout = new LocationLayout();

  /**
   * Creates a new instance.
   */
  public LocationTO() {
  }

  @XmlAttribute
  public Long getxPosition() {
    return xPosition;
  }

  public LocationTO setxPosition(
      @Nonnull
      Long xPosition
  ) {
    requireNonNull(xPosition, "xPosition");
    this.xPosition = xPosition;
    return this;
  }

  @XmlAttribute
  public Long getyPosition() {
    return yPosition;
  }

  public LocationTO setyPosition(
      @Nonnull
      Long yPosition
  ) {
    requireNonNull(yPosition, "yPosition");
    this.yPosition = yPosition;
    return this;
  }

  @XmlAttribute
  public Long getzPosition() {
    return zPosition;
  }

  public LocationTO setzPosition(
      @Nonnull
      Long zPosition
  ) {
    requireNonNull(zPosition, "zPosition");
    this.zPosition = zPosition;
    return this;
  }

  @XmlAttribute
  public String getType() {
    return type;
  }

  public LocationTO setType(
      @Nonnull
      String type
  ) {
    requireNonNull(type, "type");
    this.type = type;
    return this;
  }

  @XmlElement(name = "link", required = true)
  public List<Link> getLinks() {
    return links;
  }

  public LocationTO setLinks(
      @Nonnull
      List<Link> links
  ) {
    requireNonNull(links, "links");
    this.links = links;
    return this;
  }

  @XmlAttribute(required = true)
  public Boolean isLocked() {
    return locked;
  }

  public LocationTO setLocked(Boolean locked) {
    this.locked = locked;
    return this;
  }

  @XmlElement(required = true)
  public LocationLayout getLocationLayout() {
    return locationLayout;
  }

  public LocationTO setLocationLayout(
      @Nonnull
      LocationLayout locationLayout
  ) {
    this.locationLayout = requireNonNull(locationLayout, "locationLayout");
    return this;
  }

  @XmlAccessorType(XmlAccessType.PROPERTY)
  @XmlType(propOrder = {"point", "allowedOperations"})
  public static class Link {

    private String point = "";
    private List<AllowedOperationTO> allowedOperations = new ArrayList<>();

    /**
     * Creates a new instance.
     */
    public Link() {
    }

    @XmlAttribute(required = true)
    public String getPoint() {
      return point;
    }

    public Link setPoint(
        @Nonnull
        String point
    ) {
      requireNonNull(point, "point");
      this.point = point;
      return this;
    }

    @XmlElement(name = "allowedOperation")
    public List<AllowedOperationTO> getAllowedOperations() {
      return allowedOperations;
    }

    public Link setAllowedOperations(
        @Nonnull
        List<AllowedOperationTO> allowedOperations
    ) {
      requireNonNull(allowedOperations, "allowedOperations");
      this.allowedOperations = allowedOperations;
      return this;
    }
  }

  @XmlAccessorType(XmlAccessType.PROPERTY)
  @XmlType(
      propOrder = {"xPosition", "yPosition", "xLabelOffset", "yLabelOffset",
          "locationRepresentation", "layerId"}
  )
  public static class LocationLayout {

    private Long xPosition = 0L;
    private Long yPosition = 0L;
    private Long xLabelOffset = 0L;
    private Long yLabelOffset = 0L;
    private LocationRepresentation locationRepresentation = LocationRepresentation.DEFAULT;
    private Integer layerId = 0;

    /**
     * Creates a new instance.
     */
    public LocationLayout() {
    }

    @XmlAttribute(required = true)
    public Long getxPosition() {
      return xPosition;
    }

    public LocationLayout setxPosition(Long xPosition) {
      this.xPosition = requireNonNull(xPosition, "xPosition");
      return this;
    }

    @XmlAttribute(required = true)
    public Long getyPosition() {
      return yPosition;
    }

    public LocationLayout setyPosition(Long yPosition) {
      this.yPosition = requireNonNull(yPosition, "yPosition");
      return this;
    }

    @XmlAttribute(required = true)
    public Long getxLabelOffset() {
      return xLabelOffset;
    }

    public LocationLayout setxLabelOffset(Long xLabelOffset) {
      this.xLabelOffset = requireNonNull(xLabelOffset, "xLabelOffset");
      return this;
    }

    @XmlAttribute(required = true)
    public Long getyLabelOffset() {
      return yLabelOffset;
    }

    public LocationLayout setyLabelOffset(Long yLabelOffset) {
      this.yLabelOffset = requireNonNull(yLabelOffset, "yLabelOffset");
      return this;
    }

    @XmlAttribute(required = true)
    public LocationRepresentation getLocationRepresentation() {
      return locationRepresentation;
    }

    public LocationLayout setLocationRepresentation(LocationRepresentation locationRepresentation) {
      this.locationRepresentation = requireNonNull(
          locationRepresentation,
          "locationRepresentation"
      );
      return this;
    }

    @XmlAttribute(required = true)
    public Integer getLayerId() {
      return layerId;
    }

    public LocationLayout setLayerId(Integer layerId) {
      this.layerId = requireNonNull(layerId, "layerId");
      return this;
    }
  }
}
