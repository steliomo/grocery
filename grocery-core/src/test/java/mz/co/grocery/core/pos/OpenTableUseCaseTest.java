/**
 *
 */
package mz.co.grocery.core.pos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.pos.service.OpenTableService;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

public class OpenTableUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private CustomerPort customerPort;

	@Mock
	private SalePort salePort;

	@Mock
	private Clock clock;

	@InjectMocks
	private OpenTableUseCase openTableUseCase = new OpenTableService(this.clock, this.customerPort, this.salePort);

	private UserContext context;

	private Sale sale;

	@Before
	public void setup() {

		this.context = this.getUserContext();
		this.sale = new Sale();
		this.sale.setUnit(EntityFactory.gimme(Unit.class, UnitTemplate.VALID));
		this.sale.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));

		Mockito.when(this.clock.todayDate()).thenReturn(LocalDate.now());

	}

	@Test
	public void shouldOpenTable() throws BusinessException {

		this.openTableUseCase.openTable(this.context, this.sale);

		Mockito.verify(this.customerPort, Mockito.times(1)).createCustomer(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Customer.class));
		Mockito.verify(this.salePort, Mockito.times(1)).createSale(this.context, this.sale);

		Assert.assertEquals(this.sale.getSaleDate(), LocalDate.now());
		Assert.assertEquals(this.sale.getSaleStatus(), SaleStatus.OPENED);
		Assert.assertEquals(this.sale.getTotal(), BigDecimal.ZERO);
		Assert.assertEquals(this.sale.getSaleType(), SaleType.INSTALLMENT);
	}

	@Test
	public void shouldOpenTableWithAnExistingCustomer() throws BusinessException {
		Mockito.when(this.customerPort.findCustomerByContact(ArgumentMatchers.anyString()))
		.thenReturn(Optional.of(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID)));

		this.openTableUseCase.openTable(this.context, this.sale);

		Mockito.verify(this.customerPort, Mockito.times(0)).createCustomer(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(Customer.class));
		Mockito.verify(this.salePort, Mockito.times(1)).createSale(this.context, this.sale);

		Assert.assertEquals(this.sale.getSaleDate(), LocalDate.now());
		Assert.assertEquals(this.sale.getSaleStatus(), SaleStatus.OPENED);
		Assert.assertEquals(this.sale.getTotal(), BigDecimal.ZERO);
		Assert.assertEquals(this.sale.getSaleType(), SaleType.INSTALLMENT);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotOpenTableWithoutUnit() throws BusinessException {
		this.openTableUseCase.openTable(this.context, new Sale());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotOpenTableWithoutCustomer() throws BusinessException {
		final Sale sale = new Sale();
		sale.setUnit(EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		this.openTableUseCase.openTable(this.context, sale);
	}
}
