/**
 *
 */
package mz.co.grocery.core.rent;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.rent.in.MakeRentPaymentUseCase;
import mz.co.grocery.core.application.rent.out.RentPaymentPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.rent.service.MakeRentPaymentService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.rent.PaymentStatus;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.RentPaymentTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentServiceTest extends AbstractUnitServiceTest {

	@Mock
	private RentPort rentPort;

	@Mock
	private RentPaymentPort rentPaymentPort;

	@Mock
	private PaymentUseCase paymentUseCase;

	@InjectMocks
	private final MakeRentPaymentUseCase rentService = new MakeRentPaymentService(this.rentPort, this.rentPaymentPort, this.paymentUseCase);

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

		Mockito.when(this.rentPort.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);

		Assert.assertEquals(PaymentStatus.COMPLETE, this.rentPayment.getPaymentStatus());
	}

	@Test
	public void shouldMakePartialRentPayment() throws BusinessException {

		this.rent.calculateTotalEstimated();
		this.rentPayment.setRent(this.rent);

		this.rent.addRentPayment(this.rentPayment);

		Mockito.when(this.rentPort.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);
		Assert.assertEquals(PaymentStatus.INCOMPLETE, this.rentPayment.getPaymentStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotMakePartialRentPaymentForZeroValue() throws BusinessException {
		this.rentPayment.setPaymentValue(BigDecimal.ZERO);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotAllowRefundWhenRentReturnStatuIsNotComplete() throws BusinessException {

		this.rent.calculateTotalEstimated();
		this.rentPayment.setRent(this.rent);
		this.rent.setTotalPaid(this.rent.getTotalEstimated().add(new BigDecimal(100)));

		Mockito.when(this.rentPort.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

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
		rent.setUnit(EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		this.rentPayment.setRent(rent);
		this.rentPayment.setPaymentValue(new BigDecimal(100));

		Mockito.when(this.rentPort.findByUuid(rent.getUuid())).thenReturn(rent);

		this.rentService.makeRentPayment(this.getUserContext(), this.rentPayment);

		Assert.assertEquals(PaymentStatus.COMPLETE, rent.getPaymentStatus());
	}
}
