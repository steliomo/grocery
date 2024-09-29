/**
 *
 */
package mz.co.grocery.persistence.pos;

import java.math.BigDecimal;
import java.util.Random;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.pos.in.CancelTableUseCase;
import mz.co.grocery.core.application.pos.in.OpenTableUseCase;
import mz.co.grocery.core.application.pos.in.RegistTableItemsUseCase;
import mz.co.grocery.core.application.pos.out.SaleListner;
import mz.co.grocery.core.application.pos.out.SaleNotifier;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.BeanQualifier;
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
import mz.co.grocery.persistence.pos.adapter.SendSaleToDBListner;
import mz.co.grocery.persistence.saleable.StockBuilder;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class CancelTableUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private OpenTableUseCase openTableUseCase;

	@Inject
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private SaleNotifier saleNotifier;

	@Inject
	@BeanQualifier(SendSaleToDBListner.NAME)
	private SaleListner sendToDBListner;

	@Inject
	private RegistTableItemsUseCase registTableItemsUseCase;

	@Inject
	private CancelTableUseCase cancelTableUseCase;

	@Inject
	private StockBuilder stockBuilder;

	@Inject
	private SalePort salePort;

	private Sale table;

	private Stock stock;

	@Before
	public void setup() throws BusinessException {

		this.table = new Sale();

		final Unit unit = this.unitPort.createUnit(this.getUserContext(), EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		Customer customer = EntityFactory.gimme(Customer.class, CustomerTemplate.VALID);
		customer.setUnit(unit);

		final Random random = new Random();

		customer.setContact(customer.getContact() + String.valueOf(random.nextInt(1000)));
		customer = this.customerPort.createCustomer(this.getUserContext(), customer);

		this.table.setUnit(unit);
		this.table.setCustomer(customer);

		this.saleNotifier.registListner(this.sendToDBListner);

		this.openTableUseCase.setSaleNotifier(this.saleNotifier);
		this.table = this.openTableUseCase.openTable(this.getUserContext(), this.table);

		this.stock = this.stockBuilder.unit(unit).quantity(1).valid().build().get(0);
	}

	@Test
	public void shouldCancelTableWithoutItensUseCase() throws BusinessException {

		final Sale canceledTable = this.cancelTableUseCase.cancel(this.getUserContext(), this.table);

		Assert.assertEquals(SaleStatus.CANCELLED, canceledTable.getSaleStatus());
	}

	@Test
	public void shouldCancelTableWithtItensUseCase() throws BusinessException {

		final SaleItem saleItem = EntityFactory.gimme(SaleItem.class, SaleItemTemplate.PRODUCT);
		saleItem.setStock(this.stock);

		saleItem.setDeliveredQuantity(BigDecimal.ZERO);
		saleItem.setSale(this.table);

		this.table.addItem(saleItem);

		this.registTableItemsUseCase.registTableItems(this.getUserContext(), this.table);

		final Sale foundTable = this.salePort.findByUuid(this.table.getUuid());
		foundTable.setUnit(this.table.getUnit().get());

		final Sale canceledTable = this.cancelTableUseCase.cancel(this.getUserContext(), foundTable);

		Assert.assertEquals(SaleStatus.CANCELLED, canceledTable.getSaleStatus());
	}
}
