// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.properties.panel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import org.opentcs.guing.base.components.properties.type.Property;
import org.opentcs.guing.base.components.properties.type.StringSetProperty;
import org.opentcs.guing.common.components.dialogs.DetailsDialogContent;

/**
 * User interface to edit a string set.
 */
public abstract class StringSetPropertyEditorPanel
    extends
      JPanel
    implements
      DetailsDialogContent {

  /**
   * The property to edit.
   */
  private StringSetProperty fProperty;

  /**
   * Creates new form StringSetPropertyEditorPanel.
   */
  @SuppressWarnings("this-escape")
  public StringSetPropertyEditorPanel() {
    initComponents();
    setPreferredSize(new Dimension(350, 250));
  }

  @Override
  public void setProperty(Property property) {
    fProperty = (StringSetProperty) property;
    DefaultListModel<String> model = new DefaultListModel<>();

    for (String item : fProperty.getItems()) {
      model.addElement(item);
    }

    itemsList.setModel(model);
  }

  @Override
  public void updateValues() {
    List<String> items = new ArrayList<>();
    ListModel<String> model = itemsList.getModel();
    int size = model.getSize();

    for (int i = 0; i < size; i++) {
      items.add(model.getElementAt(i));
    }

    fProperty.setItems(items);
  }

  @Override
  public Property getProperty() {
    return fProperty;
  }

  /**
   * Edits the selected value.
   */
  protected abstract void edit();

  /**
   * Adds a new entry.
   */
  protected abstract void add();

  /**
   * Returns the list with the values.
   *
   * @return The list with the values.
   */
  protected JList<String> getItemsList() {
    return itemsList;
  }

  // FORMATTER:OFF
  // CHECKSTYLE:OFF
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    itemsScrollPane = new javax.swing.JScrollPane();
    itemsList = new javax.swing.JList<>();
    controlPanel = new javax.swing.JPanel();
    addButton = new javax.swing.JButton();
    editButton = new javax.swing.JButton();
    removeButton = new javax.swing.JButton();
    rigidArea = new javax.swing.JPanel();
    moveUpButton = new javax.swing.JButton();
    moveDownButton = new javax.swing.JButton();

    setLayout(new java.awt.BorderLayout());

    itemsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    itemsScrollPane.setViewportView(itemsList);

    add(itemsScrollPane, java.awt.BorderLayout.CENTER);

    controlPanel.setLayout(new java.awt.GridBagLayout());

    addButton.setFont(addButton.getFont());
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("i18n/org/opentcs/plantoverview/panels/propertyEditing"); // NOI18N
    addButton.setText(bundle.getString("stringSetPropertyEditorPanel.button_add.text")); // NOI18N
    addButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
    controlPanel.add(addButton, gridBagConstraints);

    editButton.setFont(editButton.getFont());
    editButton.setText(bundle.getString("stringSetPropertyEditorPanel.button_edit.text")); // NOI18N
    editButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        editButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
    controlPanel.add(editButton, gridBagConstraints);

    removeButton.setFont(removeButton.getFont());
    removeButton.setText(bundle.getString("stringSetPropertyEditorPanel.button_remove.text")); // NOI18N
    removeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        removeButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
    controlPanel.add(removeButton, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.weighty = 1.0;
    controlPanel.add(rigidArea, gridBagConstraints);

    moveUpButton.setFont(moveUpButton.getFont());
    moveUpButton.setText(bundle.getString("stringSetPropertyEditorPanel.button_up.text")); // NOI18N
    moveUpButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        moveUpButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
    controlPanel.add(moveUpButton, gridBagConstraints);

    moveDownButton.setFont(moveDownButton.getFont());
    moveDownButton.setText(bundle.getString("stringSetPropertyEditorPanel.button_down.text")); // NOI18N
    moveDownButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        moveDownButtonActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
    controlPanel.add(moveDownButton, gridBagConstraints);

    add(controlPanel, java.awt.BorderLayout.EAST);
  }// </editor-fold>//GEN-END:initComponents
  // CHECKSTYLE:ON
  // FORMATTER:ON

  private void moveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownButtonActionPerformed
    int index = itemsList.getSelectedIndex();

    if (index == -1) {
      return;
    }

    DefaultListModel<String> model = (DefaultListModel<String>) itemsList.getModel();

    if (index == model.size() - 1) {
      return;
    }

    String value = model.getElementAt(index);
    model.removeElementAt(index);
    model.insertElementAt(value, index + 1);
    itemsList.setSelectedIndex(index + 1);
  }//GEN-LAST:event_moveDownButtonActionPerformed

  private void moveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpButtonActionPerformed
    int index = itemsList.getSelectedIndex();

    if (index == -1) {
      return;
    }

    if (index == 0) {
      return;
    }

    DefaultListModel<String> model = (DefaultListModel<String>) itemsList.getModel();
    String value = model.getElementAt(index);
    model.removeElementAt(index);
    model.insertElementAt(value, index - 1);
    itemsList.setSelectedIndex(index - 1);
  }//GEN-LAST:event_moveUpButtonActionPerformed

  private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
    String value = itemsList.getSelectedValue();

    if (value == null) {
      return;
    }

    DefaultListModel<String> model = (DefaultListModel<String>) itemsList.getModel();
    model.removeElement(value);
  }//GEN-LAST:event_removeButtonActionPerformed

  private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
    edit();
  }//GEN-LAST:event_editButtonActionPerformed

  private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
    add();
  }//GEN-LAST:event_addButtonActionPerformed

  // FORMATTER:OFF
  // CHECKSTYLE:OFF
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton addButton;
  private javax.swing.JPanel controlPanel;
  private javax.swing.JButton editButton;
  private javax.swing.JList<String> itemsList;
  private javax.swing.JScrollPane itemsScrollPane;
  private javax.swing.JButton moveDownButton;
  private javax.swing.JButton moveUpButton;
  private javax.swing.JButton removeButton;
  private javax.swing.JPanel rigidArea;
  // End of variables declaration//GEN-END:variables
  // CHECKSTYLE:ON
  // FORMATTER:ON
}
