/**
 *
 */
package mz.co.grocery.core.domain.payment;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public enum Voucher {

	TEN(new NoDiscount(new BigDecimal(10))),

	TWENTY(new NoDiscount(new BigDecimal(20))),

	FIFTY(new NoDiscount(new BigDecimal(50))),

	HUNDRED(new NoDiscount(new BigDecimal(100))),

	TWO_HUNDRED(new NoDiscount(new BigDecimal(200))),

	THREE_HUNDRED(new NoDiscount(new BigDecimal(300))),

	FIVE_HUNDRED(new UptoFiveHundredDiscount(new BigDecimal(500))),

	ONE_THOUSANT(new UptoFiveHundredDiscount(new BigDecimal(1000))),

	TWO_THOUSANT(new UptoFiveHundredDiscount(new BigDecimal(2000))),

	THREE_THOUSANT(new UptoFiveHundredDiscount(new BigDecimal(3000))),

	FIVE_THOUSANT(new UptoFiveHundredDiscount(new BigDecimal(5000)));

	private final Discount discount;

	Voucher(final Discount discount) {
		this.discount = discount;
	}

	public Discount getDiscount() {
		return this.discount;
	}

}
