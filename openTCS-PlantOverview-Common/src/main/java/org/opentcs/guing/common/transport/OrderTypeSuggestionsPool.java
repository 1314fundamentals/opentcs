/**
 * Copyright (c) The openTCS Authors.
 *
 * This program is free software and subject to the MIT license. (For details,
 * see the licensing information (LICENSE.txt) you should have received with
 * this copy of the software.)
 */
package org.opentcs.guing.common.transport;

import jakarta.inject.Inject;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.TreeSet;
import org.opentcs.components.plantoverview.OrderTypeSuggestions;

/**
 * A collection of all transport order types suggested.
 */
public class OrderTypeSuggestionsPool
    implements OrderTypeSuggestions {

  /**
   * The transport order type suggestions.
   */
  private final Set<String> typeSuggestions = new TreeSet<>();

  @Inject
  public OrderTypeSuggestionsPool(Set<OrderTypeSuggestions> typeSuggestions) {
    requireNonNull(typeSuggestions, "typeSuggestions");

    for (OrderTypeSuggestions typeSuggestion : typeSuggestions) {
      this.typeSuggestions.addAll(typeSuggestion.getTypeSuggestions());
    }
  }

  @Override
  public Set<String> getTypeSuggestions() {
    return typeSuggestions;
  }

  /**
   * Adds an additional type to the pool.
   *
   * @param typeSuggestion The type to add.
   */
  public void addTypeSuggestion(String typeSuggestion) {
    typeSuggestions.add(typeSuggestion);
  }
}
