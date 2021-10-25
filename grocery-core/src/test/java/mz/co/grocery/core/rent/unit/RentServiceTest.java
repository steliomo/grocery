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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.fixturefactory.RentItemTemplate;
import mz.co.grocery.core.fixturefactory.RentTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.rent.service.RentServiceImpl;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class RentServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final RentService rentService = new RentServiceImpl();

	@Mock
	private StockDAO stockDAO;

	@Mock
	private RentItemDAO rentItemDAO;

	@Mock
	private RentDAO rentDAO;

	@Mock
	private PaymentService paymentService;

	@Mock
	private ApplicationTranslator translator;

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
				Rent.addRentItem(rentItem);
			});
		});
	}

	@Test
	public void shouldRentItems() throws BusinessException {

		Mockito.when(this.stockDAO.findByUuid(null)).thenReturn(EntityFactory.gimme(Stock.class, StockTemplate.VALID));
		final UserContext context = this.getUserContext();

		this.rentService.rent(context, this.rent);

		Mockito.verify(this.rentDAO).create(context, this.rent);
		Mockito.verify(this.rentItemDAO, Mockito.times(10)).create(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.paymentService).debitTransaction(context, this.rent.getUnit().getUuid());

		Assert.assertFalse(this.rent.getRentItems().isEmpty());
		Assert.assertEquals(this.rent.getRentItems().size(), 10);
		Assert.assertEquals(PaymentStatus.PENDING, this.rent.getPaymentStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRentForUnavailableItems() throws BusinessException {
		Mockito.when(this.stockDAO.findByUuid(null)).thenReturn(EntityFactory.gimme(Stock.class, StockTemplate.UNAVAILABLE));
		this.rentService.rent(this.getUserContext(), this.rent);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRentEmptyItems() throws BusinessException {
		final Rent rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID);
		this.rentService.rent(this.getUserContext(), rent);
	}
}