// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.data.model.visualization;

import static java.util.Objects.requireNonNull;
import static org.opentcs.util.Assertions.checkInRange;

import jakarta.annotation.Nonnull;
import java.io.Serializable;
import org.opentcs.data.model.Couple;

/**
 * A reference to an {@link Image}.
 */
public class ImageReference
    implements
      Serializable {

  /**
   * The actual reference to the image (i.e. its ID).
   */
  private final String imageRef;
  /**
   * The coordinates (in mm) where the top-left corner of the image is positioned in the plant
   * model.
   */
  private final Couple positionOffset;
  /**
   * The size of the image (in mm) along the plant model's X axis.
   * <p>
   * The size of the image along the plant model's Y axis scales according to the image's aspect
   * ratio.
   * </p>
   */
  private final long sizeX;

  /**
   * Creates a new instance.
   *
   * @param imageRef The actual reference to the image (i.e. its ID).
   * @param positionOffset The coordinates (in mm) where the top-left corner of the image is
   * positioned in the plant model.
   * @param sizeX The size of the image (in mm) along the plant model's X axis.
   */
  public ImageReference(
      @Nonnull
      String imageRef,
      @Nonnull
      Couple positionOffset,
      long sizeX
  ) {
    this.imageRef = requireNonNull(imageRef, "imageRef");
    this.positionOffset = requireNonNull(positionOffset, "positionOffset");
    this.sizeX = checkInRange(sizeX, 1, Long.MAX_VALUE, "sizeX");
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
  public ImageReference withImageRef(
      @Nonnull
      String imageRef
  ) {
    return new ImageReference(imageRef, positionOffset, sizeX);
  }

  /**
   * Returns the coordinates (in mm) where the top-left corner of the image is positioned in the
   * plant model.
   *
   * @return The coordinates (in mm) where the top-left corner of the image is positioned in the
   * plant model.
   */
  @Nonnull
  public Couple getPositionOffset() {
    return positionOffset;
  }

  /**
   * Creates a copy of this object, with the given position offset.
   *
   * @param positionOffset The value to be set in the copy.
   * @return A copy of this object, differing in the given value.
   */
  public ImageReference withPositionOffset(
      @Nonnull
      Couple positionOffset
  ) {
    return new ImageReference(imageRef, positionOffset, sizeX);
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
  public ImageReference withSizeX(long sizeX) {
    return new ImageReference(imageRef, positionOffset, sizeX);
  }

  @Override
  public String toString() {
    return "ImageReference{"
        + "imageRef=" + imageRef
        + ", positionOffset=" + positionOffset
        + ", sizeX=" + sizeX
        + '}';
  }
}
