// SPDX-FileCopyrightText: The openTCS Authors
// SPDX-License-Identifier: MIT
package org.opentcs.strategies.basic.dispatching.priorization.candidate;

import java.util.Comparator;
import org.opentcs.strategies.basic.dispatching.AssignmentCandidate;

/**
 * Compares {@link AssignmentCandidate}s by routing costs to the transport order's first
 * destination.
 * Note: this comparator imposes orderings that are inconsistent with equals.
 */
public class CandidateComparatorByInitialRoutingCosts
    implements
      Comparator<AssignmentCandidate> {

  /**
   * A key used for selecting this comparator in a configuration setting.
   * Should be unique among all keys.
   */
  public static final String CONFIGURATION_KEY = "BY_INITIAL_ROUTING_COSTS";

  /**
   * Creates a new instance.
   */
  public CandidateComparatorByInitialRoutingCosts() {
  }

  /**
   * Compares two candidates by their inital routing cost.
   * Note: this comparator imposes orderings that are inconsistent with equals.
   *
   * @see Comparator#compare(java.lang.Object, java.lang.Object)
   * @param candidate1 The first candidate.
   * @param candidate2 The second candidate.
   * @return the value 0 if candidate1 and candidate2 have the same routing cost;
   * a value less than 0 if candidate1 has lower routing cost than candidate2;
   * and a value greater than 0 otherwise.
   */
  @Override
  public int compare(AssignmentCandidate candidate1, AssignmentCandidate candidate2) {
    // Lower routing costs are better.
    return Long.compare(candidate1.getInitialRoutingCosts(), candidate2.getInitialRoutingCosts());
  }

}
