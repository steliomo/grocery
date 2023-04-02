/**
 *
 */
package mz.co.grocery.core.util;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public class BigDecimalUtil {

	public static boolean isGraterThan(final BigDecimal valueA, final BigDecimal valueB) {
		return valueA.compareTo(valueB) == BigDecimal.ONE.intValue();
	}

	public static boolean isGraterThanOrEqual(final BigDecimal valueA, final BigDecimal valueB) {
		return valueA.compareTo(valueB) == BigDecimal.ONE.intValue() || BigDecimalUtil.isEqual(valueA, valueB);
	}

	public static boolean isLessThan(final BigDecimal valueA, final BigDecimal valueB) {
		return valueA.compareTo(valueB) == BigDecimal.ONE.negate().intValue();
	}

	public static boolean isLessThanOrEqual(final BigDecimal valueA, final BigDecimal valueB) {
		return valueA.compareTo(valueB) == BigDecimal.ONE.negate().intValue() || BigDecimalUtil.isEqual(valueA, valueB);
	}

	public static boolean isEqual(final BigDecimal valueA, final BigDecimal valueB) {
		return valueA.compareTo(valueB) == BigDecimal.ZERO.intValue();
	}

	public static boolean isZero(final BigDecimal valueA) {
		return valueA.compareTo(BigDecimal.ZERO) == BigDecimal.ZERO.intValue();
	}
}
