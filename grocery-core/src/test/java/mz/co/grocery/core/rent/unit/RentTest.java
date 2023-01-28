/**
 *
 */
package mz.co.grocery.core.rent.unit;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;

/**
 * @author Stélio Moiane
 *
 */
public class RentTest {

	@Test
	public void shouldCalculateTotalToPayWhenEstimatedIsGreaterThanCalculated() {
		final Rent rent = new Rent();
		final BigDecimal value = new BigDecimal(100);
		rent.setTotalEstimated(value);

		Assert.assertEquals(value, rent.getTotalToPay());
	}

	@Test
	public void shouldCalculateTotalToPayWhenEstimatedIsEqualToCalculated() {
		final Rent rent = new Rent();
		final BigDecimal value = new BigDecimal(100);

		rent.setTotalEstimated(value);
		rent.setTotalCalculated(value);

		Assert.assertEquals(value, rent.getTotalToPay());
	}

	@Test
	public void shouldCalculateTotalToPayWhenEstimatedIsLessThanCalculated() {

		final Rent rent = new Rent();
		final BigDecimal estimated = new BigDecimal(100);
		final BigDecimal calculated = new BigDecimal(1000);

		rent.setTotalEstimated(estimated);
		rent.setTotalCalculated(calculated);

		Assert.assertEquals(calculated, rent.getTotalToPay());
	}

	@Test
	public void shouldCalculatePaymentStatusWhenPaymentIsLessThanToPay() {
		final Rent rent = new Rent();
		final BigDecimal estimated = new BigDecimal(100);
		final BigDecimal paid = new BigDecimal(10);

		rent.setTotalEstimated(estimated);
		rent.setTotalPaid(paid);

		rent.setPaymentStatus();

		Assert.assertEquals(PaymentStatus.INCOMPLETE, rent.getPaymentStatus());
	}

	@Test
	public void shouldCalculatePaymentStatusWhenPaymentEqualThanToPay() {
		final Rent rent = new Rent();
		final BigDecimal estimated = new BigDecimal(100);
		final BigDecimal paid = new BigDecimal(100);
		final BigDecimal calculated = new BigDecimal(100);

		rent.setTotalEstimated(estimated);
		rent.setTotalPaid(paid);
		rent.setTotalCalculated(calculated);

		rent.setPaymentStatus();

		Assert.assertEquals(PaymentStatus.COMPLETE, rent.getPaymentStatus());
	}

	@Test
	public void shouldCalculatePaymentStatusWhenPaymentIsGreaterThanToPay() {
		final Rent rent = new Rent();
		final BigDecimal estimated = new BigDecimal(100);
		final BigDecimal paid = new BigDecimal(200);
		final BigDecimal calculated = new BigDecimal(100);

		rent.setTotalEstimated(estimated);
		rent.setTotalPaid(paid);
		rent.setTotalCalculated(calculated);

		rent.setPaymentStatus();

		Assert.assertEquals(PaymentStatus.OVER_PAYMENT, rent.getPaymentStatus());
	}

}
