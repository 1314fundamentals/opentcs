// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v8.binding.response.converter;

import java.util.List;
import java.util.Map;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentcs.data.model.Couple;
import org.opentcs.data.model.visualization.Image;
import org.opentcs.data.model.visualization.ImageReference;
import org.opentcs.data.model.visualization.Layer;
import org.opentcs.data.model.visualization.LayerGroup;
import org.opentcs.data.model.visualization.VisualLayout;
import org.opentcs.kernel.extensions.servicewebapi.common.JsonBinder;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.response.data.VisualLayoutTO;

/**
 * Tests for {@link VisualLayoutConverter}.
 */
class VisualLayoutConverterTest {

  private JsonBinder jsonBinder;
  private VisualLayoutConverter visualLayoutConverter;

  @BeforeEach
  void setUp() {
    jsonBinder = new JsonBinder();
    visualLayoutConverter = new VisualLayoutConverter();
  }

  @Test
  void convert() {
    VisualLayout visualLayout = new VisualLayout("visual-layout-1")
        .withProperties(Map.of("key-1", "value-1"))
        .withScaleX(54.3)
        .withScaleY(34.5)
        .withLayers(
            List.of(
                new Layer(1, 2, true, "layer-1", 3)
                    .withBackgroundImage(
                        new ImageReference(
                            "image-id-1",
                            new Couple(12, 34),
                            56
                        )
                    ),
                new Layer(2, 3, true, "layer-2", 4)
                    .withBackgroundImage(null)
            )
        )
        .withLayerGroups(List.of(new LayerGroup(4, "layer-group-1", true)))
        .withImages(Map.of("image-id-1", new Image("image-id-1", "media-type-1", "data-1")));

    VisualLayoutTO result = visualLayoutConverter.convert(visualLayout);

    Approvals.verify(jsonBinder.toJson(result));
  }
}
