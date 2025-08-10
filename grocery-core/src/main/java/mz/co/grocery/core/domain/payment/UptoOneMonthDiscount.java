/**
 *
 */
package mz.co.grocery.core.domain.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Stélio Moiane
 *
 */
public class UptoOneMonthDiscount implements Discount {

	private final BigDecimal discountValue = new BigDecimal(0.05);

	private Integer days;

	public UptoOneMonthDiscount(final Integer days) {
		this.days = days;
	}

	@Override
	public BigDecimal getDiscount() {
		return Discount.SUBSCRITION_BASE_VALUE.multiply(new BigDecimal(this.days)).multiply(this.discountValue).setScale(1, RoundingMode.FLOOR);
	}

	@Override
	public BigDecimal getTotal() {
		return Discount.SUBSCRITION_BASE_VALUE.multiply(new BigDecimal(this.days)).subtract(this.getDiscount());
	}

	@Override
	public Integer getDays() {
		return this.days;
	}
}
