// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.shared.CoupleTO;

// CHECKSTYLE:OFF
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@JsonPropertyOrder(alphabetic = true)
public class LayerTO {

  @JsonProperty(value = "id", required = true)
  private final int id;
  @JsonProperty(value = "ordinal", required = true)
  private final int ordinal;
  @JsonProperty(value = "visible", required = true)
  private final boolean visible;
  @Nonnull
  @JsonProperty(value = "name", required = true)
  private final String name;
  @JsonProperty(value = "groupId", required = true)
  private final int groupId;
  @Nullable
  private ImageReferenceTO backgroundImage;

  @RequiredArgsConstructor
  @Getter
  @Setter
  @EqualsAndHashCode
  @ToString
  @Accessors(chain = true)
  @JsonPropertyOrder(alphabetic = true)
  public static class ImageReferenceTO {

    @Nonnull
    @JsonProperty(value = "imageRef", required = true)
    private final String imageRef;
    @Nonnull
    @JsonProperty(value = "positionOffset", required = true)
    private final CoupleTO positionOffset;
    @JsonProperty(value = "sizeX", required = true)
    private final long sizeX;
  }
}
// CHECKSTYLE:ON
