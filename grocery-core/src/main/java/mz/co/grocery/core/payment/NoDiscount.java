/**
 *
 */
package mz.co.grocery.core.payment;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public class NoDiscount implements Discount {

	private final BigDecimal voucher;

	public NoDiscount(final BigDecimal voucher) {
		this.voucher = voucher;
	}

	@Override
	public BigDecimal calculate() {
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal getVoucher() {
		return this.voucher;
	}
}
