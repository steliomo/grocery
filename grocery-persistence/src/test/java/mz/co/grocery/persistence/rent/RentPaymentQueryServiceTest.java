/**
 *
 */
package mz.co.grocery.persistence.rent;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.service.RentPaymentQueryService;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.RentPaymentTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private RentService rentService;

	@Inject
	private RentPaymentService rentPaymentService;

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	private RentPaymentQueryService rentPaymentQueryService;

	private Rent rent;

	private RentPayment rentPayment;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.rentBuilder.build();
		this.rentService.rent(this.getUserContext(), this.rent);

		this.rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID, processor -> {
			if (processor instanceof RentPayment) {
				final RentPayment payment = (RentPayment) processor;
				payment.setRent(this.rent);
			}
		});

		this.rentPaymentService.makeRentPayment(this.getUserContext(), this.rentPayment);
	}

	@Test
	public void shouldFindRentSalesByUnitAndPeriod() throws BusinessException {

		final LocalDate startDate = LocalDate.now(), endDate = startDate;

		final List<SaleReport> rentSales = this.rentPaymentQueryService.findSalesByUnitAndPeriod(this.rent.getUnit().getUuid(), startDate, endDate);

		Assert.assertFalse(rentSales.isEmpty());
		Assert.assertTrue(rentSales.get(0).getSale().compareTo(this.rentPayment.getPaymentValue()) == 0);
	}


	@Test
	public void shouldFindRentSalesByUnitAndMonthlyPeriod() throws BusinessException {

		final LocalDate startDate = LocalDate.now(), endDate = startDate;

		final List<SaleReport> rentSales = this.rentPaymentQueryService.findSalesByUnitAndMonthlyPeriod(this.rent.getUnit().getUuid(), startDate, endDate);

		Assert.assertFalse(rentSales.isEmpty());
		Assert.assertTrue(rentSales.get(0).getSale().compareTo(this.rentPayment.getPaymentValue()) == 0);
	}
}
