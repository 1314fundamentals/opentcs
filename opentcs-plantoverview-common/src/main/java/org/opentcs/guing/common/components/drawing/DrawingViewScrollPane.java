// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.guing.common.components.drawing;

import static java.util.Objects.requireNonNull;

import jakarta.annotation.Nonnull;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import org.jhotdraw.gui.PlacardScrollPaneLayout;
import org.opentcs.guing.common.components.drawing.course.Origin;
import org.opentcs.guing.common.components.drawing.course.OriginChangeListener;

/**
 * A custom scroll pane to wrap an <code>OpenTCSDrawingView</code>.
 */
public class DrawingViewScrollPane
    extends
      JScrollPane
    implements
      OriginChangeListener {

  /**
   * The drawing view.
   */
  private final OpenTCSDrawingView drawingView;
  /**
   * The view's placard panel.
   */
  private final DrawingViewPlacardPanel placardPanel;
  /**
   * Whether the rulers are currently visible or not.
   */
  private boolean rulersVisible = true;
  private Origin origin = new Origin();

  /**
   * Creates a new instance.
   *
   * @param drawingView The drawing view.
   * @param placardPanel The view's placard panel.
   */
  @SuppressWarnings("this-escape")
  public DrawingViewScrollPane(
      OpenTCSDrawingView drawingView,
      DrawingViewPlacardPanel placardPanel
  ) {
    this.drawingView = requireNonNull(drawingView, "drawingView");
    this.placardPanel = requireNonNull(placardPanel, "placardPanel");

    setViewport(new JViewport());
    getViewport().setView(drawingView.getComponent());
    setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    setViewportBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    setLayout(new PlacardScrollPaneLayout());
    setBorder(new EmptyBorder(0, 0, 0, 0));

    // Horizontal and vertical rulers
    Ruler.Horizontal newHorizontalRuler = new Ruler.Horizontal(drawingView);
    drawingView.addPropertyChangeListener(newHorizontalRuler);
    newHorizontalRuler.setPreferredWidth(drawingView.getComponent().getWidth());
    Ruler.Vertical newVerticalRuler = new Ruler.Vertical(drawingView);
    drawingView.addPropertyChangeListener(newVerticalRuler);
    newVerticalRuler.setPreferredHeight(drawingView.getComponent().getHeight());
    setColumnHeaderView(newHorizontalRuler);
    setRowHeaderView(newVerticalRuler);

    this.add(placardPanel, JScrollPane.LOWER_LEFT_CORNER);

    // Increase the preferred height of the horizontal scroll bar, which also affects the height of
    // the corner in which the DrawingViewPlacardPanel is located. This ensures there is enough
    // vertical space for all components in all graphical environments. (Without this, the vertical
    // space is not sufficient for some components e.g. on Ubuntu 20.04.)
    getHorizontalScrollBar().setPreferredSize(new Dimension(100, 34));

    // Register handler for rulers toggle button.
    placardPanel.getToggleRulersButton().addItemListener(
        new RulersToggleListener(placardPanel.getToggleRulersButton())
    );
    placardPanel.getToggleRulersButton().setSelected(rulersVisible);
  }

  public OpenTCSDrawingView getDrawingView() {
    return drawingView;
  }

  public DrawingViewPlacardPanel getPlacardPanel() {
    return placardPanel;
  }

  public Ruler.Horizontal getHorizontalRuler() {
    return (Ruler.Horizontal) getColumnHeader().getView();
  }

  public Ruler.Vertical getVerticalRuler() {
    return (Ruler.Vertical) getRowHeader().getView();
  }

  public boolean isRulersVisible() {
    return rulersVisible;
  }

  public void setRulersVisible(boolean visible) {
    this.rulersVisible = visible;
    if (visible) {
      getHorizontalRuler().setVisible(true);
      getHorizontalRuler().setPreferredWidth(getWidth());
      getVerticalRuler().setVisible(true);
      getVerticalRuler().setPreferredHeight(getHeight());
      getPlacardPanel().getToggleRulersButton().setSelected(true);
    }
    else {
      getHorizontalRuler().setVisible(false);
      getHorizontalRuler().setPreferredSize(new Dimension(0, 0));
      getVerticalRuler().setVisible(false);
      getVerticalRuler().setPreferredSize(new Dimension(0, 0));
      getPlacardPanel().getToggleRulersButton().setSelected(false);
    }
  }

  public void originChanged(
      @Nonnull
      Origin origin
  ) {
    requireNonNull(origin, "origin");
    if (origin == this.origin) {
      return;
    }

    this.origin.removeListener(getHorizontalRuler());
    this.origin.removeListener(getVerticalRuler());
    this.origin.removeListener(this);
    this.origin = origin;

    origin.addListener(getHorizontalRuler());
    origin.addListener(getVerticalRuler());
    origin.addListener(this);

    // Notify the rulers directly. This is necessary to initialize/update the rulers scale when a
    // model is created or loaded.
    // Calling origin.notifyScaleChanged() would lead to all model elements being notified (loading
    // times for bigger models would suffer).
    getHorizontalRuler().originScaleChanged(new EventObject(origin));
    getVerticalRuler().originScaleChanged(new EventObject(origin));
  }

  @Override
  public void originLocationChanged(EventObject evt) {
  }

  @Override
  public void originScaleChanged(EventObject evt) {
    drawingView.getComponent().revalidate();
  }

  private class RulersToggleListener
      implements
        ItemListener {

    private final JToggleButton rulersButton;

    RulersToggleListener(JToggleButton rulersButton) {
      this.rulersButton = requireNonNull(rulersButton, "rulersButton");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
      setRulersVisible(rulersButton.isSelected());
    }
  }
}
