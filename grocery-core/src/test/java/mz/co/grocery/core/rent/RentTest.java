/**
 *
 */
package mz.co.grocery.core.rent;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.rent.model.LoadStatus;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.ReturnStatus;

/**
 * @author Stélio Moiane
 *
 */
public class RentTest extends AbstractUnitServiceTest {

	@Test
	public void shouldFindRentPaymentBaseCalculationWhenReturnIsPendingOrImcomplete() {
		final RentItem rentItem = new RentItem();
		rentItem.addLoadedQuantity(new BigDecimal(20));
		rentItem.addReturnedQuantity(new BigDecimal(15));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		final BigDecimal estimated = new BigDecimal(1000);
		final BigDecimal calculated = new BigDecimal(500);

		rent.setTotalEstimated(estimated);
		rent.setTotalCalculated(calculated);
		rent.setReturnStatus();

		Assert.assertEquals(estimated, rent.paymentBaseCalculation());
	}

	@Test
	public void shouldFindRentPaymentBaseCalculationWhenReturnIscomplete() {
		final RentItem rentItem = new RentItem();
		rentItem.addLoadedQuantity(new BigDecimal(20));
		rentItem.addReturnedQuantity(new BigDecimal(20));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		final BigDecimal estimated = new BigDecimal(1000);
		final BigDecimal calculated = new BigDecimal(500);

		rent.setTotalEstimated(estimated);
		rent.setTotalCalculated(calculated);
		rent.setReturnStatus();

		Assert.assertEquals(calculated, rent.paymentBaseCalculation());
	}

	@Test
	public void shouldCalculatePaymentStatusWhenPaymentIsZero() {
		final Rent rent = new Rent();
		final BigDecimal estimated = new BigDecimal(100);
		final BigDecimal paid = new BigDecimal(0);

		rent.setTotalEstimated(estimated);
		rent.setTotalPaid(paid);

		rent.setPaymentStatus();

		Assert.assertEquals(PaymentStatus.PENDING, rent.getPaymentStatus());
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

		final RentItem rentItem = new RentItem();
		rentItem.addLoadedQuantity(new BigDecimal(10));
		rentItem.addReturnedQuantity(new BigDecimal(10));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		final BigDecimal estimated = new BigDecimal(100);
		final BigDecimal paid = new BigDecimal(200);
		final BigDecimal calculated = new BigDecimal(200);

		rent.setTotalEstimated(estimated);
		rent.setTotalPaid(paid);
		rent.setTotalCalculated(calculated);
		rent.setReturnStatus();

		rent.setPaymentStatus();

		Assert.assertEquals(PaymentStatus.COMPLETE, rent.getPaymentStatus());
	}

	@Test
	public void shouldCalculatePaymentStatusWhenPaymentIsGreaterThanToPay() {
		final RentItem rentItem = new RentItem();
		rentItem.addLoadedQuantity(new BigDecimal(20));
		rentItem.addReturnedQuantity(new BigDecimal(20));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		final BigDecimal estimated = new BigDecimal(3900);
		final BigDecimal paid = new BigDecimal(3900);
		final BigDecimal calculated = new BigDecimal(3850);
		rent.addRentItem(rentItem);

		rent.setTotalEstimated(estimated);
		rent.setTotalPaid(paid);
		rent.setTotalCalculated(calculated);
		rent.setReturnStatus();

		rent.setPaymentStatus();

		Assert.assertEquals(calculated, rent.paymentBaseCalculation());
		Assert.assertEquals(new BigDecimal(50), rent.totalToRefund());
		Assert.assertEquals(PaymentStatus.OVER_PAYMENT, rent.getPaymentStatus());
	}

	@Test
	public void shouldSetLoadingStatusToIncompleteWhenThereIsMoreRentItensToLoad() {

		final RentItem rentItem = new RentItem();
		rentItem.setPlannedQuantity(new BigDecimal(20));
		rentItem.addLoadedQuantity(new BigDecimal(10));
		rentItem.setLoadStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		rent.setLoadingStatus();

		Assert.assertEquals(LoadStatus.INCOMPLETE, rent.getLoadingStatus());
	}

	@Test
	public void shouldSetLoadingStatusToCompleteWhenThereIsNoMoreRentItensToLoad() {

		final RentItem rentItem = new RentItem();
		rentItem.setPlannedQuantity(new BigDecimal(20));
		rentItem.addLoadedQuantity(new BigDecimal(20));
		rentItem.setLoadStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		rent.setLoadingStatus();

		Assert.assertEquals(LoadStatus.COMPLETE, rent.getLoadingStatus());
	}

	@Test
	public void shouldSetReturnStatusToIncompleteWhenThereIsMoreRentItensToReturn() {

		final RentItem rentItem = new RentItem();
		rentItem.addLoadedQuantity(new BigDecimal(20));
		rentItem.addReturnedQuantity(new BigDecimal(10));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		rent.setReturnStatus();

		Assert.assertEquals(ReturnStatus.INCOMPLETE, rent.getReturnStatus());
	}

	@Test
	public void shouldSetReturnStatusToCompleteWhenThereIsNoMoreRentItensToReturn() {

		final RentItem rentItem = new RentItem();
		rentItem.addLoadedQuantity(new BigDecimal(20));
		rentItem.addReturnedQuantity(new BigDecimal(20));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);

		rent.setReturnStatus();

		Assert.assertEquals(ReturnStatus.COMPLETE, rent.getReturnStatus());
	}
}
