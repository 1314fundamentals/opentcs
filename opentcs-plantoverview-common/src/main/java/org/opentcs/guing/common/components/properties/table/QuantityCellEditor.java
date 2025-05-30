// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties.table;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.opentcs.guing.base.components.properties.type.AbstractQuantity;
import org.opentcs.guing.base.components.properties.type.ModelAttribute;
import org.opentcs.guing.base.model.elements.LayoutModel;
import org.opentcs.guing.common.util.I18nPlantOverview;
import org.opentcs.guing.common.util.UserMessageHelper;
import org.opentcs.thirdparty.guing.common.jhotdraw.util.ResourceBundleUtil;

/**
 * A cell editor for a quantity property.
 */
public class QuantityCellEditor
    extends
      AbstractPropertyCellEditor {

  /**
   * Creates a new instance of QuantityCellEditor
   *
   * @param textField
   * @param umh
   */
  @SuppressWarnings("this-escape")
  public QuantityCellEditor(JTextField textField, UserMessageHelper umh) {
    super(textField, umh);
    setStyle(textField);
  }

  /**
   * Initialises the style of the text field.
   *
   * @param textField
   */
  private void setStyle(JTextField textField) {
    setClickCountToStart(1);
    textField.setHorizontalAlignment(JTextField.LEFT);
  }

  /**
   * Returns the property for this editor.
   *
   * @return The property for this editor.
   */
  protected AbstractQuantity<?> property() {
    return (AbstractQuantity<?>) fProperty;
  }

  /**
   * Extracts the value and the unit from the text in the text field.
   * If the text cannot be parsed, the property is not changed.
   *
   * @param text The text to extract the value and unit from.
   */
  protected void extractQuantity(String text) {

    int blankIndex = text.indexOf(' ');
    // No space means wrong format ( Number[SPACE]Unit )
    if (blankIndex == -1) {
      showCellEditingErrorMsg("quantityCellEditor.dialog_errorFormat.message");
      return;
    }

    String valueString = text.substring(0, blankIndex);
    String unitString = text.substring(blankIndex + 1);

    // Check if unitString is a valid unit
    if (!property().isPossibleUnit(unitString)) {
      showCellEditingErrorMsg(
          "quantityCellEditor.dialog_errorUnit.message",
          property().getPossibleUnits()
      );
      return;
    }

    double newValue;
    try {
      newValue = Double.parseDouble(valueString);
    }
    catch (NumberFormatException e) {
      showCellEditingErrorMsg("quantityCellEditor.dialog_errorNumber.message");
      return;
    }

    // Check if value is inside the valid range
    if (!property().getValidRange().isValueValid(newValue)) {
      showCellEditingErrorMsg(
          "quantityCellEditor.dialog_errorRange.message",
          property().getValidRange().getMin(),
          property().getValidRange().getMax()
      );
      return;
    }

    // For the layoutModel the Scaling cannot be 0
    if (property().getModel() instanceof LayoutModel && newValue == 0) {
      showCellEditingErrorMsg("quantityCellEditor.dialog_errorScale.message");
      return;
    }

    // Check if property will change
    try {
      double oldValue = (double) property().getValue();
      String oldUnit = property().getUnit().toString();

      if (newValue != oldValue || !unitString.equals(oldUnit)) {
        markProperty();
      }
    }
    catch (ClassCastException e) {
      markProperty();
    }

    // Change property
    property().setValueAndUnit(newValue, unitString);

  }

  @Override
  public boolean stopCellEditing() {
    // ChangeState.DETAIL_CHANGED is unwanted at this point and is set in
    // StandardDetailsDialog. If we wouldn't change it here the model value
    // would be copied to the layout. By changing the ChangeState to CHANGED
    // it will only be saved in the model.
    if (property().getChangeState() == ModelAttribute.ChangeState.DETAIL_CHANGED) {
      property().setChangeState(ModelAttribute.ChangeState.CHANGED);
    }

    return super.stopCellEditing();
  }

  @Override  // DefaultCellEditor
  public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column
  ) {

    setValue(value);
    ((JTextField) getComponent()).setText(property().toString());

    return fComponent;
  }

  @Override  // DefaultCellEditor
  public Object getCellEditorValue() {
    JTextField textField = (JTextField) getComponent();
    String text = textField.getText();

    extractQuantity(text);

    return property();
  }

  private void showCellEditingErrorMsg(String resourceName, Object... arguments) {
    ResourceBundleUtil bundle = ResourceBundleUtil.getBundle(I18nPlantOverview.PROPERTIES_PATH);
    userMessageHelper.showMessageDialog(
        bundle.getString("quantityCellEditor.dialog_error.title"),
        bundle.getFormatted(resourceName, arguments),
        UserMessageHelper.Type.ERROR
    );
  }

  private void showCellEditingErrorMsg(String resourceName) {
    showCellEditingErrorMsg(resourceName, "");
  }
}
