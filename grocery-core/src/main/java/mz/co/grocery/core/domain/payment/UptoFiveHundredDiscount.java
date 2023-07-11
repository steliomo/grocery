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
public class UptoFiveHundredDiscount implements Discount {

	private final BigDecimal discountValue = new BigDecimal(0.05);

	private final BigDecimal voucher;

	public UptoFiveHundredDiscount(final BigDecimal voucher) {
		this.voucher = voucher;
	}

	@Override
	public BigDecimal calculate() {
		return this.voucher.multiply(this.discountValue).setScale(1, RoundingMode.FLOOR);
	}

	@Override
	public BigDecimal getVoucher() {
		return this.voucher;
	}
}
