/**
 *
 */
package mz.co.grocery.core.payment;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.domain.payment.Discount;
import mz.co.grocery.core.domain.payment.Voucher;

/**
 * @author Stélio Moiane
 *
 */
public class DiscountCalculationTest {

	@Test
	public void shouldCalculateDiscount() {

		final Discount discount = Voucher.QUARTERLY.getDiscount();

		Assert.assertEquals(discount.getDiscount(), BigDecimal.ZERO);
	}
}
