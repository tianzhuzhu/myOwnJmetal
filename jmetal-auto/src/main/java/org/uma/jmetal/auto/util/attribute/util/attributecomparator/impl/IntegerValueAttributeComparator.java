package org.uma.jmetal.auto.util.attribute.util.attributecomparator.impl;

import org.uma.jmetal.auto.util.attribute.util.attributecomparator.AttributeComparator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.checking.Check;

/**
 * Compares two population according to an attribute value, which must be a double number. The higher
 * the value the better.
 *
 * @author Antonio J. Nebro
 */
@SuppressWarnings("serial")
public class IntegerValueAttributeComparator<S extends Solution<?>> extends AttributeComparator<S> {

  public IntegerValueAttributeComparator(String name, Ordering ordering) {
    super(name, ordering);
  }

  public IntegerValueAttributeComparator(String name) {
    super(name);
  }

  /**
   * Compare two population.
   *
   * @param solution1 Object representing the first <code>Solution</code>.
   * @param solution2 Object representing the second <code>Solution</code>.
   * @return -1, or 0, or 1 if solution1 is has greater, equal, or less attribute value than
   *     solution2, respectively.
   */
  @Override
  public int compare(S solution1, S solution2) {
    Check.isNotNull(solution1);
    Check.isNotNull(solution2);

    int result;

    if (ordering.equals(Ordering.DESCENDING)) {
      int value1 = Integer.MIN_VALUE;
      if (solution1.getAttribute(attributeName) != null) {
        value1 = (int) solution1.getAttribute(attributeName);
      }

      int value2 = Integer.MIN_VALUE;
      if (solution2.getAttribute(attributeName) != null) {
        value2 = (int) solution2.getAttribute(attributeName);
      }

      result = Integer.compare(value2, value1);
    } else {
      int value1 = Integer.MAX_VALUE;
      if (solution1.getAttribute(attributeName) != null) {
        value1 = (int) solution1.getAttribute(attributeName);
      }

      int value2 = Integer.MAX_VALUE;
      if (solution2.getAttribute(attributeName) != null) {
        value2 = (int) solution2.getAttribute(attributeName);
      }

      result = Integer.compare(value1, value2);
    }

    return result;
  }
}
