/**
 *
 */
package mz.co.grocery.core.domain.payment;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public interface Discount {
	BigDecimal calculate();

	BigDecimal getVoucher();
}
