// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.converter;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.opentcs.access.to.model.CoupleCreationTO;
import org.opentcs.access.to.model.ImageCreationTO;
import org.opentcs.access.to.model.ImageReferenceCreationTO;
import org.opentcs.access.to.model.LayerCreationTO;
import org.opentcs.access.to.model.LayerGroupCreationTO;
import org.opentcs.access.to.model.VisualLayoutCreationTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.LayerGroupTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.LayerTO;
import org.opentcs.kernel.extensions.servicewebapi.v8.binding.request.data.VisualLayoutTO;

/**
 * Includes the conversion methods for all VisualLayout classes.
 */
public class VisualLayoutConverter {

  @Inject
  public VisualLayoutConverter() {
  }

  public VisualLayoutCreationTO toVisualLayoutCreationTO(VisualLayoutTO vLayout) {
    return new VisualLayoutCreationTO(vLayout.getName())
        .withProperties(Optional.ofNullable(vLayout.getProperties()).orElse(Map.of()))
        .withScaleX(vLayout.getScaleX())
        .withScaleY(vLayout.getScaleY())
        .withLayers(convertLayers(vLayout.getLayers()))
        .withLayerGroups(convertLayerGroups(vLayout.getLayerGroups()))
        .withImages(convertImages(vLayout.getImages()));
  }

  private List<LayerGroupCreationTO> convertLayerGroups(List<LayerGroupTO> layerGroups) {
    return layerGroups.stream()
        .map(
            layerGroup -> new LayerGroupCreationTO(
                layerGroup.getId(),
                layerGroup.getName(),
                layerGroup.isVisible()
            )
        )
        .collect(Collectors.toList());
  }

  private List<LayerCreationTO> convertLayers(List<LayerTO> layers) {
    return layers.stream()
        .map(
            layer -> new LayerCreationTO(
                layer.getId(),
                layer.getOrdinal(),
                layer.isVisible(),
                layer.getName(),
                layer.getGroupId()
            )
                .withBackgroundImage(convertImageReference(layer.getBackgroundImage()))
        )
        .collect(Collectors.toList());
  }

  private Map<String, ImageCreationTO> convertImages(Map<String, VisualLayoutTO.ImageTO> images) {
    return images.values().stream()
        .collect(
            Collectors.toMap(
                VisualLayoutTO.ImageTO::getId,
                image -> new ImageCreationTO(
                    image.getId(),
                    image.getMediaType(),
                    image.getData()
                )
            )
        );
  }

  private ImageReferenceCreationTO convertImageReference(LayerTO.ImageReferenceTO imageReference) {
    if (imageReference == null) {
      return null;
    }

    return new ImageReferenceCreationTO(
        imageReference.getImageRef(),
        new CoupleCreationTO(
            imageReference.getPositionOffset().getX(),
            imageReference.getPositionOffset().getY()
        ),
        imageReference.getSizeX()
    );
  }
}
