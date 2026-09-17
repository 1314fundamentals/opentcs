// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT

package org.opentcs.kernel.extensions.servicewebapi.v8.binding.response.data;

import java.util.List;
import java.util.Map;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentcs.kernel.extensions.servicewebapi.common.JsonBinder;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.response.data.shared.CoupleTO;

/**
 * Tests for {@link VisualLayoutTO}.
 */
class VisualLayoutTOTest {
  private JsonBinder jsonBinder;

  @BeforeEach
  void setUp() {
    jsonBinder = new JsonBinder();
  }

  @Test
  void jsonSampleMinimal() {
    Approvals.verify(jsonBinder.toJson(createVisualLayoutMinimal()));
  }

  @Test
  void jsonSampleFull() {
    Approvals.verify(jsonBinder.toJson(createVisualLayoutFull()));
  }

  private VisualLayoutTO createVisualLayoutMinimal() {
    return new VisualLayoutTO()
        .setName("some-visual-layout")
        .setProperties(Map.of())
        .setScaleX(1.2)
        .setScaleY(3.4)
        .setLayers(List.of())
        .setLayerGroups(List.of())
        .setImages(Map.of());
  }

  private VisualLayoutTO createVisualLayoutFull() {
    return new VisualLayoutTO()
        .setName("some-visual-layout")
        .setProperties(
            Map.of(
                "some-key", "some-value",
                "some-other-key", "some-other-value"
            )
        )
        .setScaleX(1.2)
        .setScaleY(3.4)
        .setLayers(
            List.of(
                new VisualLayoutTO.LayerTO()
                    .setId(5)
                    .setOrdinal(6)
                    .setName("some-layer")
                    .setVisible(true)
                    .setGroupId(7)
                    .setBackgroundImage(
                        new VisualLayoutTO.LayerTO.ImageReferenceTO()
                            .setImageRef("some-id")
                            .setPositionOffset(
                                new CoupleTO()
                                    .setX(8)
                                    .setY(9)
                            )
                            .setSizeX(10)
                    ),
                new VisualLayoutTO.LayerTO()
                    .setId(11)
                    .setOrdinal(12)
                    .setName("some-other-layer")
                    .setVisible(true)
                    .setGroupId(13)
                    .setBackgroundImage(null)
            )
        )
        .setLayerGroups(
            List.of(
                new VisualLayoutTO.LayerGroupTO()
                    .setId(14)
                    .setName("some-layer-group")
                    .setVisible(true)
            )
        )
        .setImages(
            Map.of(
                "some-id",
                new VisualLayoutTO.ImageTO()
                    .setId("some-id")
                    .setMediaType("some-media-type")
                    .setData("some-data")
            )
        );
  }
}
