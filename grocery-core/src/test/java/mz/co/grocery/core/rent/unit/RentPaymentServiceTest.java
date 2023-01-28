/**
 *
 */
package mz.co.grocery.core.rent.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.builder.RentUnitBuilder;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentPaymentServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final RentPaymentService rentService = new RentPaymentServiceImpl();

	@Mock
	private RentDAO rentDAO;

	@Mock
	private RentPaymentDAO rentPaymentDAO;

	@Mock
	private PaymentService paymentService;

	private Rent rent;

	@Before
	public void setup() {
		this.rent = new RentUnitBuilder().withUnloadedProducts(10).withUnloadedServices(10).calculatePlannedTotal().build();
	}

	@Test
	public void shouldMakeFullRentPayment() throws BusinessException {

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID);
		this.rent.calculateTotalEstimated();

		rentPayment.setRent(this.rent);
		this.rent.addRentPayment(rentPayment);
		rentPayment.setPaymentValue(this.rent.getTotalEstimated());

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), rentPayment);

		Assert.assertEquals(PaymentStatus.COMPLETE, rentPayment.getPaymentStatus());
	}

	@Test
	public void shouldMakePartialRentPayment() throws BusinessException {

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID);
		this.rent.calculateTotalEstimated();
		rentPayment.setRent(this.rent);

		this.rent.addRentPayment(rentPayment);

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), rentPayment);
		Assert.assertEquals(PaymentStatus.INCOMPLETE, rentPayment.getPaymentStatus());
	}
}
