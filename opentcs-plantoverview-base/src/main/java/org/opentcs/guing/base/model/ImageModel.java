// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.base.model;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import org.opentcs.data.model.visualization.Image;

/**
 * A representation of an {@link Image}.
 */
public class ImageModel {

  private final String id;
  private final String mediaType;
  private final String data;

  /**
   * Creates a new instance.
   *
   * @param id The image's ID.
   * @param mediaType The image's media type.
   * @param data The image data as a base64-encoded string.
   */
  public ImageModel(
      @Nonnull
      String id,
      @Nonnull
      String mediaType,
      @Nonnull
      String data
  ) {
    this.id = requireNonNull(id, "id");
    this.mediaType = requireNonNull(mediaType, "key");
    this.data = requireNonNull(data, "data");
  }

  @Nonnull
  public String getId() {
    return id;
  }

  @Nonnull
  public String getMediaType() {
    return mediaType;
  }

  @Nonnull
  public String getData() {
    return data;
  }
}
