// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.components.plantoverview;

import jakarta.annotation.Nonnull;
import java.awt.Image;
import org.opentcs.data.model.Location;
import org.opentcs.data.model.LocationType;
import org.opentcs.data.model.visualization.LocationRepresentation;

/**
 * Provides a location theme.
 */
public interface LocationTheme {

  /**
   * Returns the image for the given location representation.
   *
   * @param representation The representation for which to return the image.
   * @return The image for the given location representation.
   */
  @Nonnull
  Image getImageFor(
      @Nonnull
      LocationRepresentation representation
  );

  /**
   * Returns the image for the given location (type).
   *
   * @param location The location to base the image on.
   * @param locationType The location type for the location.
   * @return The image for the give location.
   */
  @Nonnull
  Image getImageFor(
      @Nonnull
      Location location,
      @Nonnull
      LocationType locationType
  );
}
