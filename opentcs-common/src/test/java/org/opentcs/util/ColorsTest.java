// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import org.junit.jupiter.api.Test;

/**
 */
class ColorsTest {

  @Test
  void testEncodeToHexRGB() {
    assertEquals("#000000", Colors.encodeToHexRGB(new Color(0, 0, 0)));
    assertEquals("#FFFFFF", Colors.encodeToHexRGB(new Color(255, 255, 255)));
    assertEquals("#FF0000", Colors.encodeToHexRGB(new Color(255, 0, 0)));
    assertEquals("#00FF00", Colors.encodeToHexRGB(new Color(0, 255, 0)));
    assertEquals("#0000FF", Colors.encodeToHexRGB(new Color(0, 0, 255)));
    assertEquals("#000001", Colors.encodeToHexRGB(new Color(0, 0, 1)));
    assertEquals("#000100", Colors.encodeToHexRGB(new Color(0, 1, 0)));
    assertEquals("#010000", Colors.encodeToHexRGB(new Color(1, 0, 0)));
  }

  @Test
  void testDecodeFromHexRGB() {
    Color color;

    color = Colors.decodeFromHexRGB("#000000");
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());

    color = Colors.decodeFromHexRGB("#FFFFFF");
    assertEquals(255, color.getRed());
    assertEquals(255, color.getGreen());
    assertEquals(255, color.getBlue());

    color = Colors.decodeFromHexRGB("#010000");
    assertEquals(1, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(0, color.getBlue());

    color = Colors.decodeFromHexRGB("#000100");
    assertEquals(0, color.getRed());
    assertEquals(1, color.getGreen());
    assertEquals(0, color.getBlue());

    color = Colors.decodeFromHexRGB("#000001");
    assertEquals(0, color.getRed());
    assertEquals(0, color.getGreen());
    assertEquals(1, color.getBlue());
  }

}
