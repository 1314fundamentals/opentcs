// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.access.to.model;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import java.io.Serializable;

/**
 * A wrapper for image data.
 */
public class ImageCreationTO
    implements
      Serializable {

  /**
   * The image's (unique) ID.
   */
  private final String id;
  /**
   * The image's media type.
   */
  private final String mediaType;
  /**
   * The image data as a base64-encoded string.
   */
  private final String data;

  /**
   * Creates a new image.
   *
   * @param id The image's (unique) ID.
   * @param mediaType The image's media type (according to the IANA's registry of MIME media types;
   * e.g. {@code image/png}).
   * @param data The image data as a base64-encoded string.
   */
  public ImageCreationTO(
      @Nonnull
      String id,
      @Nonnull
      String mediaType,
      @Nonnull
      String data
  ) {
    this.id = requireNonNull(id, "id");
    this.mediaType = requireNonNull(mediaType, "mediaType");
    this.data = requireNonNull(data, "data");
  }

  /**
   * Returns the image's (unique) ID.
   *
   * @return The image's (unique) ID.
   */
  public String getId() {
    return id;
  }

  /**
   * Creates a copy of this object, with the given ID.
   *
   * @param id The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageCreationTO withId(
      @Nonnull
      String id
  ) {
    return new ImageCreationTO(id, mediaType, data);
  }

  /**
   * Returns The image's media type (according to the IANA's registry of MIME media types; e.g.
   * {@code image/png}).
   *
   * @return The image's media type.
   */
  @Nonnull
  public String getMediaType() {
    return mediaType;
  }

  /**
   * Creates a copy of this object, with the given media type.
   *
   * @param mediaType The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageCreationTO withMediaType(
      @Nonnull
      String mediaType
  ) {
    return new ImageCreationTO(id, mediaType, data);
  }

  /**
   * Returns the image data as a base64-encoded string.
   *
   * @return The image data as a base64-encoded string.
   */
  @Nonnull
  public String getData() {
    return data;
  }

  /**
   * Creates a copy of this object, with the given data.
   *
   * @param data The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageCreationTO withData(
      @Nonnull
      String data
  ) {
    return new ImageCreationTO(id, mediaType, data);
  }

  @Override
  public String toString() {
    return "ImageCreationTO{"
        + "id=" + id
        + ", mediaType=" + mediaType
        + ", data=" + data
        + '}';
  }
}
