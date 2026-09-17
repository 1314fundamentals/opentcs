// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.converter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentcs.access.to.model.ImageCreationTO;
import org.opentcs.access.to.model.ImageReferenceCreationTO;
import org.opentcs.access.to.model.LayerCreationTO;
import org.opentcs.access.to.model.LayerGroupCreationTO;
import org.opentcs.access.to.model.VisualLayoutCreationTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.LayerGroupTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.LayerTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.VisualLayoutTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.shared.CoupleTO;

/**
 * Tests for {@link VisualLayoutConverter}.
 */
class VisualLayoutConverterTest {

  private VisualLayoutConverter visualLayoutConverter;

  private Map<String, String> propertyMap;

  @BeforeEach
  void setUp() {
    visualLayoutConverter = new VisualLayoutConverter();

    propertyMap = Map.of("some-key", "some-value");
  }

  @Test
  void checkToVisualLayoutCreationTO() {
    VisualLayoutTO vLayout = new VisualLayoutTO("V1")
        .setScaleX(50.0)
        .setScaleY(50.0)
        .setLayers(
            List.of(
                new LayerTO(1, 2, true, "L1", 3)
                    .setBackgroundImage(
                        new LayerTO.ImageReferenceTO(
                            "some-image-id",
                            new CoupleTO(100, 200),
                            300
                        )
                    )
            )
        )
        .setLayerGroups(List.of(new LayerGroupTO(1, "Lg1", true)))
        .setImages(
            Map.of(
                "some-image-id",
                new VisualLayoutTO.ImageTO("some-image-id", "some-media-type", "some-data")
            )
        )
        .setProperties(propertyMap);

    VisualLayoutCreationTO result = visualLayoutConverter.toVisualLayoutCreationTO(vLayout);

    assertThat(result.getName()).isEqualTo("V1");
    assertThat(result.getScaleX()).isEqualTo(50.0);
    assertThat(result.getScaleY()).isEqualTo(50.0);
    assertThat(result.getLayers()).hasSize(1);
    assertThat(result.getLayers().getFirst())
        .returns(1, LayerCreationTO::getId)
        .returns(2, LayerCreationTO::getOrdinal)
        .returns(true, LayerCreationTO::isVisible)
        .returns("L1", LayerCreationTO::getName)
        .returns(3, LayerCreationTO::getGroupId)
        .satisfies(
            imageReference -> assertThat(imageReference.getBackgroundImage())
                .returns("some-image-id", ImageReferenceCreationTO::getImageRef)
                .returns(100L, ref -> ref.getPositionOffset().getX())
                .returns(200L, ref -> ref.getPositionOffset().getY())
                .returns(300L, ImageReferenceCreationTO::getSizeX)
        );
    assertThat(result.getLayerGroups()).hasSize(1);
    assertThat(result.getLayerGroups().getFirst())
        .returns(1, LayerGroupCreationTO::getId)
        .returns("Lg1", LayerGroupCreationTO::getName)
        .returns(true, LayerGroupCreationTO::isVisible);
    assertThat(result.getImages())
        .hasSize(1)
        .containsKey("some-image-id");
    assertThat(result.getImages().get("some-image-id"))
        .returns("some-image-id", ImageCreationTO::getId)
        .returns("some-media-type", ImageCreationTO::getMediaType)
        .returns("some-data", ImageCreationTO::getData);
    assertThat(result.getProperties()).hasSize(1);
    assertThat(result.getProperties()).isEqualTo(propertyMap);
  }
}
