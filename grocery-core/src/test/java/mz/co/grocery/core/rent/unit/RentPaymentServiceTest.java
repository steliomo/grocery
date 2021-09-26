/**
 *
 */
package mz.co.grocery.core.rent.unit;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.core.fixturefactory.RentTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
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

		this.rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID, result -> {

			if (result instanceof Grocery || result instanceof Customer) {
				return;
			}

			final Rent Rent = (Rent) result;
			final List<RentItem> products = EntityFactory.gimme(RentItem.class, 5, RentItemTemplate.PRODUCT);
			final List<RentItem> services = EntityFactory.gimme(RentItem.class, 5, RentItemTemplate.SERVICE);

			final List<RentItem> rentItems = Stream.concat(products.stream(), services.stream()).collect(Collectors.toList());

			rentItems.forEach(rentItem -> {
				rentItem.setTotal();
				Rent.addRentItem(rentItem);
			});
		});

	}

	@Test
	public void shouldMakeFullRentPayment() throws BusinessException {

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID);
		rentPayment.setRent(this.rent);
		this.rent.addRentPayment(rentPayment);
		rentPayment.setPaymentValue(this.rent.getTotalRent());

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), rentPayment);
		Assert.assertEquals(PaymentStatus.COMPLETE, rentPayment.getPaymentStatus());
	}

	@Test
	public void shouldMakePartialRentPayment() throws BusinessException {

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID);
		rentPayment.setRent(this.rent);
		this.rent.addRentPayment(rentPayment);

		Mockito.when(this.rentDAO.findByUuid(this.rent.getUuid())).thenReturn(this.rent);

		this.rentService.makeRentPayment(this.getUserContext(), rentPayment);
		Assert.assertEquals(PaymentStatus.INCOMPLETE, rentPayment.getPaymentStatus());
	}

}
