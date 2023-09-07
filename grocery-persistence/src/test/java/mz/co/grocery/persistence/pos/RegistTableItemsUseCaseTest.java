/**
 *
 */
package mz.co.grocery.persistence.pos;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.pos.in.RegistTableItemsUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.CustomerTemplate;
import mz.co.grocery.persistence.fixturefactory.SaleItemTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.grocery.persistence.saleable.StockBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class RegistTableItemsUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private OpenTableUseCase openTableUseCase;

	@Inject
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private RegistTableItemsUseCase registTableItemsUseCase;

	@Inject
	private StockBuilder stockBuilder;

	private Sale table;

	private Stock stock;

	@Before
	public void setup() throws BusinessException {
		this.table = new Sale();

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(unit);
		customer.setContact(customer.getContact() + "70");
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);

		this.table.setUnit(unit);
		this.table.setCustomer(customer);

		this.table = this.openTableUseCase.openTable(this.getUserContext(), this.table);

		this.stock = this.stockBuilder.unit(unit).quantity(1).valid().build().get(0);

	}

	@Test
	public void shouldRegistTableItems() throws BusinessException {

		final SaleItem saleItem = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.PRODUCT);
		saleItem.setStock(this.stock);

		saleItem.setDeliveredQuantity(BigDecimal.ZERO);
		saleItem.setSale(this.table);

		this.table.addItem(saleItem);

		this.table = this.registTableItemsUseCase.registTableItems(this.getUserContext(), this.table);

		Assert.assertEquals(this.table.getSaleStatus(), SaleStatus.IN_PROGRESS);
		Assert.assertThat(this.table.getTotal(), Matchers.comparesEqualTo(saleItem.getTotalSaleItem()));
	}
}
