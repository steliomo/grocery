/**
 *
 */
package mz.co.grocery.core.domain.payment;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public class NoDiscount implements Discount {

	private Integer days;

	public NoDiscount(final Integer days) {
		this.days = days;
	}

	@Override
	public BigDecimal getDiscount() {
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getTotal() {
		return Discount.SUBSCRITION_BASE_VALUE.multiply(new BigDecimal(this.days));
	}

	@Override
	public Integer getDays() {
		return this.days;
	}
}
