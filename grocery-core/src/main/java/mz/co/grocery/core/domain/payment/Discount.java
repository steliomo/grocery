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

	BigDecimal SUBSCRITION_BASE_VALUE = new BigDecimal(10);

	BigDecimal getDiscount();

	BigDecimal getTotal();

	Integer getDays();
}
