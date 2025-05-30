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
    propOrder = {"name", "xPosition", "yPosition", "zPosition", "vehicleOrientationAngle",
        "type", "vehicleEnvelopes", "outgoingPaths", "properties", "pointLayout"}
)
public class PointTO
    extends
      PlantModelElementTO {

  private Long xPosition = 0L;
  private Long yPosition = 0L;
  private Long zPosition = 0L;
  private Float vehicleOrientationAngle = 0.0F;
  private Type type = Type.HALT_POSITION;
  private List<VehicleEnvelopeTO> vehicleEnvelopes = new ArrayList<>();
  private List<OutgoingPath> outgoingPaths = new ArrayList<>();
  private PointLayout pointLayout = new PointLayout();

  /**
   * Creates a new instance.
   */
  public PointTO() {
  }

  @XmlAttribute(required = true)
  public Long getxPosition() {
    return xPosition;
  }

  public PointTO setxPosition(
      @Nonnull
      Long xPosition
  ) {
    requireNonNull(xPosition, "xPosition");
    this.xPosition = xPosition;
    return this;
  }

  @XmlAttribute(required = true)
  public Long getyPosition() {
    return yPosition;
  }

  public PointTO setyPosition(
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

  public PointTO setzPosition(
      @Nonnull
      Long zPosition
  ) {
    requireNonNull(zPosition, "zPosition");
    this.zPosition = zPosition;
    return this;
  }

  @XmlAttribute
  public Float getVehicleOrientationAngle() {
    return vehicleOrientationAngle;
  }

  public PointTO setVehicleOrientationAngle(
      @Nonnull
      Float vehicleOrientationAngle
  ) {
    requireNonNull(vehicleOrientationAngle, "vehicleOrientationAngle");
    this.vehicleOrientationAngle = vehicleOrientationAngle;
    return this;
  }

  @XmlAttribute(required = true)
  public Type getType() {
    return type;
  }

  public PointTO setType(
      @Nonnull
      Type type
  ) {
    requireNonNull(type, "type");
    this.type = type;
    return this;
  }

  @XmlElement(name = "outgoingPath")
  public List<OutgoingPath> getOutgoingPaths() {
    return outgoingPaths;
  }

  public PointTO setOutgoingPaths(
      @Nonnull
      List<OutgoingPath> outgoingPath
  ) {
    requireNonNull(outgoingPath, "outgoingPath");
    this.outgoingPaths = outgoingPath;
    return this;
  }

  @XmlElement(name = "vehicleEnvelope")
  public List<VehicleEnvelopeTO> getVehicleEnvelopes() {
    return vehicleEnvelopes;
  }

  public PointTO setVehicleEnvelopes(
      @Nonnull
      List<VehicleEnvelopeTO> vehicleEnvelopes
  ) {
    this.vehicleEnvelopes = requireNonNull(vehicleEnvelopes, "vehicleEnvelopes");
    return this;
  }

  @XmlElement(required = true)
  public PointLayout getPointLayout() {
    return pointLayout;
  }

  public PointTO setPointLayout(
      @Nonnull
      PointLayout pointLayout
  ) {
    this.pointLayout = requireNonNull(pointLayout, "pointLayout");
    return this;
  }

  @XmlType(name = "Type")
  public enum Type {
    // CHECKSTYLE:OFF
    HALT_POSITION,
    PARK_POSITION;
    // CHECKSTYLE:ON
  }

  @XmlAccessorType(XmlAccessType.PROPERTY)
  public static class OutgoingPath {

    private String name = "";

    /**
     * Creates a new instance.
     */
    public OutgoingPath() {
    }

    @XmlAttribute(required = true)
    public String getName() {
      return name;
    }

    public OutgoingPath setName(
        @Nonnull
        String name
    ) {
      requireNonNull(name, "name");
      this.name = name;
      return this;
    }
  }

  @XmlAccessorType(XmlAccessType.PROPERTY)
  @XmlType(propOrder = {"xPosition", "yPosition", "xLabelOffset", "yLabelOffset", "layerId"})
  public static class PointLayout {

    private Long xPosition = 0L;
    private Long yPosition = 0L;
    private Long xLabelOffset = 0L;
    private Long yLabelOffset = 0L;
    private Integer layerId = 0;

    /**
     * Creates a new instance.
     */
    public PointLayout() {
    }

    @XmlAttribute(required = true)
    public Long getxPosition() {
      return xPosition;
    }

    public PointLayout setxPosition(
        @Nonnull
        Long xPosition
    ) {
      this.xPosition = requireNonNull(xPosition, "xPosition");
      return this;
    }

    @XmlAttribute(required = true)
    public Long getyPosition() {
      return yPosition;
    }

    public PointLayout setyPosition(
        @Nonnull
        Long yPosition
    ) {
      this.yPosition = requireNonNull(yPosition, "yPosition");
      return this;
    }

    @XmlAttribute(required = true)
    public Long getxLabelOffset() {
      return xLabelOffset;
    }

    public PointLayout setxLabelOffset(
        @Nonnull
        Long xLabelOffset
    ) {
      this.xLabelOffset = requireNonNull(xLabelOffset, "xLabelOffset");
      return this;
    }

    @XmlAttribute(required = true)
    public Long getyLabelOffset() {
      return yLabelOffset;
    }

    public PointLayout setyLabelOffset(
        @Nonnull
        Long yLabelOffset
    ) {
      this.yLabelOffset = requireNonNull(yLabelOffset, "yLabelOffset");
      return this;
    }

    @XmlAttribute(required = true)
    public Integer getLayerId() {
      return layerId;
    }

    public PointLayout setLayerId(
        @Nonnull
        Integer layerId
    ) {
      this.layerId = requireNonNull(layerId, "layerId");
      return this;
    }
  }
}
