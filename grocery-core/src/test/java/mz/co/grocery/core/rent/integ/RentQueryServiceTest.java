/**
 *
 */
package mz.co.grocery.core.rent.integ;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.rent.builder.RentBuilder;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.ReturnStatus;
import mz.co.grocery.core.rent.service.RentQueryService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class RentQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private RentBuilder builder;

	@Inject
	private RentService rentService;

	@Inject
	private RentQueryService rentQueryService;

	private Rent rent;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.builder.build();
		this.rentService.rent(this.getUserContext(), this.rent);
	}

	@Test
	public void shouldFindPendingPaymentRentsByCustomer() throws BusinessException {
		final List<Rent> rents = this.rentQueryService.findPendingPaymentRentsByCustomer(this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());
		rents.forEach(rent -> {
			Assert.assertEquals(PaymentStatus.PENDING, rent.getPaymentStatus());
		});
	}

	@Test
	public void shouldFindPendingDevolutionRentsByCustomer() throws BusinessException {
		final List<Rent> rents = this.rentQueryService.fetchPendingDevolutionRentsByCustomer(this.rent.getCustomer().getUuid());

		Assert.assertFalse(rents.isEmpty());

		rents.forEach(rent -> {
			rent.getRentItems().stream().forEach(rentItem -> {
				Assert.assertEquals(ReturnStatus.PENDING, rentItem.getReturnStatus());

				if (rentItem.getType().equals(ItemType.PRODUCT)) {
					Assert.assertEquals(ItemType.PRODUCT, rentItem.getType());
				} else {
					Assert.assertEquals(ItemType.SERVICE, rentItem.getType());
				}

				Assert.assertNotNull(rentItem.getItem());
			});
		});
	}
}
