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

import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.RentPaymentTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentPortTest extends AbstractIntegServiceTest {

	@Inject
	private RentPort rentPort;

	@Inject
	private RentPaymentPort rentPaymentPort;

	@Inject
	private RentBuilder rentBuilder;

	private Rent rent;

	private RentPayment rentPayment;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.rentBuilder.build();
		this.rent = this.rentPort.createRent(this.getUserContext(), this.rent);

		this.rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID, processor -> {
			if (processor instanceof RentPayment) {
				final RentPayment payment = (RentPayment) processor;
				payment.setRent(this.rent);
			}
		});

		this.rentPayment = this.rentPaymentPort.createRentPayment(this.getUserContext(), this.rentPayment);
	}

	@Test
	public void shouldFindRentSalesByUnitAndPeriod() throws BusinessException {

		final LocalDate startDate = LocalDate.now(), endDate = startDate;

		final List<SaleReport> rentSales = this.rentPaymentPort.findSalesByUnitAndPeriod(this.rent.getUnit().get().getUuid(), startDate, endDate);

		Assert.assertFalse(rentSales.isEmpty());
		Assert.assertTrue(rentSales.get(0).getSale().compareTo(this.rentPayment.getPaymentValue()) == 0);
	}

	@Test
	public void shouldFindRentSalesByUnitAndMonthlyPeriod() throws BusinessException {

		final LocalDate startDate = LocalDate.now(), endDate = startDate;

		final List<SaleReport> rentSales = this.rentPaymentPort.findSalesByUnitAndMonthlyPeriod(this.rent.getUnit().get().getUuid(), startDate,
				endDate);

		Assert.assertFalse(rentSales.isEmpty());
		Assert.assertTrue(rentSales.get(0).getSale().compareTo(this.rentPayment.getPaymentValue()) == 0);
	}
}
