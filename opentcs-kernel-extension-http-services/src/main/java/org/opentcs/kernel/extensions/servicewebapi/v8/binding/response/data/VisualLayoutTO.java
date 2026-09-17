// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v8.binding.response.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.response.data.shared.CoupleTO;

// CHECKSTYLE:OFF
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@JsonPropertyOrder(alphabetic = true)
public class VisualLayoutTO {

  @Nonnull
  private String name;
  @Nonnull
  @JsonPropertyOrder(alphabetic = true)
  private Map<String, String> properties;
  private double scaleX;
  private double scaleY;
  @Nonnull
  private List<LayerTO> layers;
  @Nonnull
  private List<LayerGroupTO> layerGroups;
  @Nonnull
  private Map<String, ImageTO> images;

  @NoArgsConstructor
  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
  @Accessors(chain = true)
  @JsonPropertyOrder(alphabetic = true)
  public static class LayerTO {

    private int id;
    private int ordinal;
    @Nonnull
    private String name;
    private boolean visible;
    private int groupId;
    @Nullable
    private ImageReferenceTO backgroundImage;

    @NoArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @Accessors(chain = true)
    @JsonPropertyOrder(alphabetic = true)
    public static class ImageReferenceTO {

      @Nonnull
      private String imageRef;
      @Nonnull
      private CoupleTO positionOffset;
      private long sizeX;
    }
  }

  @NoArgsConstructor
  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
  @Accessors(chain = true)
  @JsonPropertyOrder(alphabetic = true)
  public static class LayerGroupTO {

    private int id;
    @Nonnull
    private String name;
    private boolean visible;
  }

  @NoArgsConstructor
  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
  @Accessors(chain = true)
  @JsonPropertyOrder(alphabetic = true)
  public static class ImageTO {

    @Nonnull
    private String id;
    @Nonnull
    private String mediaType;
    @Nonnull
    private String data;
  }
}
