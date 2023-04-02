/**
 *
 */
package mz.co.grocery.core.rent.unit;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.builder.RentUnitBuilder;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentPaymentServiceImpl;
import mz.co.grocery.core.util.ApplicationTranslator;
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

	@Mock
	private ApplicationTranslator translator;

	private Rent rent;

	private RentPayment rentPayment;

	@Before
	public void setup() {
		this.rent = new RentUnitBuilder().withUnloadedProducts(10).withUnloadedServices(10).calculatePlannedTotal().build();
		this.rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID);
	}

	@Test
	public void shouldMakeFullRentPayment() throws BusinessException {

		this.rent.calculateTotalEstimated();

		this.rentPayment.setRent(this.rent);
		this.rent.addRentPayment(this.rentPayment);
		this.rentPayment.setPaymentValue(this.rent.getTotalEstimated());

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);

		Assert.assertEquals(PaymentStatus.COMPLETE, this.rentPayment.getPaymentStatus());
	}

	@Test
	public void shouldMakePartialRentPayment() throws BusinessException {

		this.rent.calculateTotalEstimated();
		this.rentPayment.setRent(this.rent);

		this.rent.addRentPayment(this.rentPayment);

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);
		Assert.assertEquals(PaymentStatus.INCOMPLETE, this.rentPayment.getPaymentStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotMakePartialRentPaymentForZeroValue() throws BusinessException {
		this.rentPayment.setPaymentValue(BigDecimal.ZERO);

		Mockito.when(this.translator.getTranslation(ArgumentMatchers.any())).thenReturn("The rent payment value cannot be zero!");

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotAllowRefundWhenRentReturnStatuIsNotComplete() throws BusinessException {

		this.rent.calculateTotalEstimated();
		this.rentPayment.setRent(this.rent);
		this.rent.setTotalPaid(this.rent.getTotalEstimated().add(new BigDecimal(100)));

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);
		Mockito.when(this.translator.getTranslation(ArgumentMatchers.any()))
		.thenReturn("The refund process cannot happen while there are items to return!");

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);
	}

	@Test
	public void shouldNotAllowRefundValuesAboveToTheExpected() throws BusinessException {
		final RentItem rentItem = EntityFactory.gimme(RentItem.class, RentItemTemplate.PRODUCT);
		rentItem.calculatePlannedTotal();
		rentItem.addLoadedQuantity(new BigDecimal(10));
		rentItem.addReturnedQuantity(new BigDecimal(10));
		rentItem.setReturnStatus();

		final Rent rent = new Rent();
		rent.addRentItem(rentItem);
		rent.calculateTotalEstimated();
		rent.setReturnStatus();
		rent.setTotalCalculated(rent.getTotalEstimated());
		rent.setTotalPaid(rent.getTotalEstimated().add(new BigDecimal(100)));
		rent.setUnit(EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		this.rentPayment.setRent(rent);
		this.rentPayment.setPaymentValue(new BigDecimal(100));

		Mockito.when(this.rentDAO.findByUuid(rent.getUuid())).thenReturn(rent);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);

		Assert.assertEquals(PaymentStatus.COMPLETE, rent.getPaymentStatus());
	}
}
