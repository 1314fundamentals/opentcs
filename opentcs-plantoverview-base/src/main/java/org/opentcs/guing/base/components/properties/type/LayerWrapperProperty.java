// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.base.components.properties.type;

import org.opentcs.guing.base.components.layer.LayerWrapper;
import org.opentcs.guing.base.model.ModelComponent;

/**
 * A property that contains a {@link LayerWrapper} instance.
 */
public class LayerWrapperProperty
    extends
      AbstractComplexProperty {

  /**
   * Creates a new instance.
   *
   * @param model The model component.
   * @param value The layer wrapper.
   */
  public LayerWrapperProperty(ModelComponent model, LayerWrapper value) {
    super(model);
    fValue = value;
  }

  @Override
  public Object getComparableValue() {
    return getValue().getLayer().getId();
  }

  @Override
  public String toString() {
    return getValue().getLayer().getName();
  }

  @Override
  public void copyFrom(Property property) {
    LayerWrapperProperty layerWrapperProperty = (LayerWrapperProperty) property;
    setValue(layerWrapperProperty.getValue());
  }

  @Override
  public LayerWrapper getValue() {
    return (LayerWrapper) super.getValue();
  }
}
