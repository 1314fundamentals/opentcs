// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.base.components.properties.type;

import java.util.Map;
import java.util.stream.Collectors;
import org.opentcs.guing.base.model.ImageModel;
import org.opentcs.guing.base.model.ModelComponent;

/**
 * A property for a map of images.
 */
public class ImagesProperty
    extends
      AbstractComplexProperty {

  /**
   * Creates a new instance with a value.
   *
   * @param model The model component.
   * @param value The value.
   */
  @SuppressWarnings("this-escape")
  public ImagesProperty(ModelComponent model, Map<String, ImageModel> value) {
    super(model);
    setValue(value);
  }

  @Override
  public Object getComparableValue() {
    return String.valueOf(fValue);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Map<String, ImageModel> getValue() {
    return (Map<String, ImageModel>) super.getValue();
  }

  @Override
  public String toString() {
    return getValue().keySet().stream()
        .sorted()
        .collect(Collectors.joining(", "));
  }
}
