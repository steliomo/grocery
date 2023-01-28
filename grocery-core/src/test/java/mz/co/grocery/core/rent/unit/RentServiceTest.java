/**
 *
 */
package mz.co.grocery.core.rent.unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.RentTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.builder.RentUnitBuilder;
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

		final RentUnitBuilder builder = new RentUnitBuilder();
		this.rent = builder.withUnloadedProducts(10).withUnloadedServices(10).build();
	}

	@Test
	public void shouldRentItems() throws BusinessException {

		Mockito.when(this.stockDAO.findByUuid(null)).thenReturn(EntityFactory.gimme(Stock.class, StockTemplate.VALID));
		final UserContext context = this.getUserContext();

		this.rentService.rent(context, this.rent);

		Mockito.verify(this.rentDAO).create(context, this.rent);
		Mockito.verify(this.rentItemDAO, Mockito.times(20)).create(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.paymentService).debitTransaction(context, this.rent.getUnit().getUuid());

		Assert.assertFalse(this.rent.getRentItems().isEmpty());
		Assert.assertEquals(this.rent.getRentItems().size(), 20);
		Assert.assertEquals(PaymentStatus.PENDING, this.rent.getPaymentStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRentForUnavailableItems() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.UNAVAILABLE);
		Mockito.when(this.stockDAO.findByUuid(null)).thenReturn(stock);

		Mockito.when(this.translator.getTranslation("product.quantity.unavailable", new String[] { stock.getName() }))
		.thenReturn("Cannot perfrom rent with unavailable " + stock.getName());

		this.rentService.rent(this.getUserContext(), this.rent);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRentEmptyItems() throws BusinessException {
		final Rent rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID);
		Mockito.when(this.translator.getTranslation("cannot.create.rent.without.items"))
		.thenReturn("Cannot perform rent process when the items are empty!");
		this.rentService.rent(this.getUserContext(), rent);
	}
}