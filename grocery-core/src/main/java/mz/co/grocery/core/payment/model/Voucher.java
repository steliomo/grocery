/**
 *
 */
package mz.co.grocery.core.payment.model;

import java.math.BigDecimal;

import mz.co.grocery.core.payment.Discount;
import mz.co.grocery.core.payment.NoDiscount;
import mz.co.grocery.core.payment.UptoFiveHundredDiscount;

/**
 * @author Stélio Moiane
 *
 */
public enum Voucher {

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
