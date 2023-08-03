/**
 *
 */
package mz.co.grocery.core.rent;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.rent.in.RentUseCase;
import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.rent.service.RentService;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.rent.PaymentStatus;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.fixturefactory.RentTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class RentServiceTest extends AbstractUnitServiceTest {

	@Mock
	private StockPort stockPort;

	@Mock
	private RentItemPort rentItemPort;

	@Mock
	private RentPort rentPort;

	@Mock
	private PaymentUseCase paymentUseCase;

	@InjectMocks
	private final RentUseCase rentUseCase = new RentService(this.rentPort, this.stockPort, this.rentItemPort, this.paymentUseCase);

	private Rent rent;

	@Before
	public void setup() {

		final RentUnitBuilder builder = new RentUnitBuilder();
		this.rent = builder.withUnloadedProducts(10).withUnloadedServices(10).build();
	}

	@Test
	public void shouldRentItems() throws BusinessException {

		Mockito.when(this.stockPort.findStockByUuid(null)).thenReturn(EntityFactory.gimme(Stock.class, StockTemplate.VALID));
		final UserContext context = this.getUserContext();

		Mockito.when(this.rentPort.createRent(context, this.rent)).thenReturn(this.rent);

		this.rentUseCase.rent(context, this.rent);

		Mockito.verify(this.rentPort).createRent(context, this.rent);
		Mockito.verify(this.rentItemPort, Mockito.times(20)).createRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.paymentUseCase).debitTransaction(context, this.rent.getUnit().get().getUuid());

		Assert.assertFalse(this.rent.getRentItems().get().isEmpty());
		Assert.assertEquals(this.rent.getRentItems().get().size(), 20);
		Assert.assertEquals(PaymentStatus.PENDING, this.rent.getPaymentStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRentForUnavailableItems() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.UNAVAILABLE);
		Mockito.when(this.stockPort.findStockByUuid(null)).thenReturn(stock);

		final Rent rent = new Rent();

		this.rent.getRentItems().get().forEach(rentItem -> rentItem.setStockable());

		this.rent.getRentItems().get().stream().filter(rentItem -> rentItem.isStockable()).forEach(rentItem -> rent.addRentItem(rentItem));

		rent.setCustomer(this.rent.getCustomer().get());
		rent.setUnit(this.rent.getUnit().get());

		this.rentUseCase.rent(this.getUserContext(), rent);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRentEmptyItems() throws BusinessException {
		final Rent rent = EntityFactory.gimme(Rent.class, RentTemplate.VALID);

		this.rentUseCase.rent(this.getUserContext(), rent);
	}

	@Test
	public void shouldAddNewRentItemsIntheRentInProgress() throws BusinessException {

		Mockito.when(this.stockPort.findStockByUuid(null)).thenReturn(EntityFactory.gimme(Stock.class, StockTemplate.VALID));
		Mockito.when(
				this.rentPort.findRentByCustomerAndUnitAndStatus(this.rent.getCustomer().get(), this.rent.getUnit().get(), RentStatus.OPENED))
		.thenReturn(Optional.ofNullable(this.rent));

		this.rent = this.rentUseCase.rent(this.getUserContext(), this.rent);

		Mockito.verify(this.rentPort, Mockito.times(0)).createRent(ArgumentMatchers.any(), ArgumentMatchers.any());
		Mockito.verify(this.rentPort, Mockito.times(1)).updateRent(ArgumentMatchers.any(), ArgumentMatchers.any());

		Assert.assertEquals(RentStatus.OPENED, this.rent.getRentStatus());
	}

	@Test
	public void shouldRentItemsForAnExistingRent() throws BusinessException {

		Mockito.when(this.stockPort.findStockByUuid(null)).thenReturn(EntityFactory.gimme(Stock.class, StockTemplate.VALID));
		final UserContext context = this.getUserContext();

		Mockito.when(this.rentPort.findRentByCustomerAndUnitAndStatus(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
		.thenReturn(Optional.of(this.rent));

		this.rentUseCase.rent(context, this.rent);

		Mockito.verify(this.rentPort).updateRent(context, this.rent);
		Mockito.verify(this.rentItemPort, Mockito.times(20)).createRentItem(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(RentItem.class));
		Mockito.verify(this.paymentUseCase).debitTransaction(context, this.rent.getUnit().get().getUuid());

		Assert.assertFalse(this.rent.getRentItems().get().isEmpty());
		Assert.assertEquals(this.rent.getRentItems().get().size(), 20);
		Assert.assertEquals(PaymentStatus.PENDING, this.rent.getPaymentStatus());
	}
}