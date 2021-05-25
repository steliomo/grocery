/**
 *
 */
package mz.co.grocery.core.payment.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.payment.Discount;
import mz.co.grocery.core.payment.model.Voucher;

/**
 * @author Stélio Moiane
 *
 */
public class DiscountCalculationTest {

	@Test
	public void shouldCalculateDiscount() {

		final Discount discount = Voucher.THREE_THOUSANT.getDiscount();
		discount.calculate();

		Assert.assertEquals(discount.calculate(), new BigDecimal(150).setScale(1, RoundingMode.FLOOR));
	}
}
