// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.access.to.model;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import java.io.Serializable;

/**
 * A reference to an {@link ImageCreationTO}.
 */
public class ImageReferenceCreationTO
    implements
      Serializable {

  /**
   * The actual reference to the image (i.e. its ID).
   */
  private final String imageRef;
  /**
   * The coordinates (in mm) where the top-left corner of the image is positioned.
   */
  private final CoupleCreationTO positionOffset;
  /**
   * The size of the image (in mm) along the plant model's X axis.
   * <p>
   * The size of the image along the plant model's Y axis scales according to the image's aspect
   * ratio.
   * </p>
   */
  private final long sizeX;

  /**
   * Creates a new image.
   *
   * @param imageRef The actual reference to the image (i.e. its ID).
   * @param positionOffset The coordinates (in mm) where the top-left corner of the image is
   * positioned.
   * @param sizeX The size of the image (in mm) along the plant model's X axis.
   */
  public ImageReferenceCreationTO(
      @Nonnull
      String imageRef,
      @Nonnull
      CoupleCreationTO positionOffset,
      long sizeX
  ) {
    this.imageRef = requireNonNull(imageRef, "imageRef");
    this.positionOffset = requireNonNull(positionOffset, "positionOffset");
    this.sizeX = sizeX;
  }

  /**
   * Returns the actual reference to the image (i.e. its ID).
   *
   * @return The actual reference to the image (i.e. its ID).
   */
  @Nonnull
  public String getImageRef() {
    return imageRef;
  }

  /**
   * Creates a copy of this object, with the given image reference.
   *
   * @param imageRef The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageReferenceCreationTO withImageRef(
      @Nonnull
      String imageRef
  ) {
    return new ImageReferenceCreationTO(imageRef, positionOffset, sizeX);
  }

  /**
   * Returns the coordinates (in mm) where the top-left corner of the image is positioned.
   *
   * @return The coordinates (in mm) where the top-left corner of the image is positioned.
   */
  @Nonnull
  public CoupleCreationTO getPositionOffset() {
    return positionOffset;
  }

  /**
   * Creates a copy of this object, with the given position offset.
   *
   * @param positionOffset The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageReferenceCreationTO withPositionOffset(
      @Nonnull
      CoupleCreationTO positionOffset
  ) {
    return new ImageReferenceCreationTO(imageRef, positionOffset, sizeX);
  }

  /**
   * Returns the size of the image (in mm) along the plant model's X axis.
   * <p>
   * The size of the image along the plant model's Y axis scales according to the image's aspect
   * ratio.
   * </p>
   *
   * @return The size of the image (in mm) along the plant model's X axis.
   */
  public long getSizeX() {
    return sizeX;
  }

  /**
   * Creates a copy of this object, with the given size along the plant model's X axis.
   *
   * @param sizeX The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageReferenceCreationTO withSizeX(long sizeX) {
    return new ImageReferenceCreationTO(imageRef, positionOffset, sizeX);
  }

  @Override
  public String toString() {
    return "ImageReferenceCreationTO{"
        + "imageRef=" + imageRef
        + ", positionOffset=" + positionOffset
        + ", sizeX=" + sizeX
        + '}';
  }
}
