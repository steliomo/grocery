/**
 *
 */
package mz.co.grocery.core.sale.unit;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.inventory.dao.InventoryDAO;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.dao.SaleItemDAO;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.sale.service.SaleServiceImpl;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public class SaleTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final SaleService saleService = new SaleServiceImpl();

	@Mock
	private SaleDAO saleDAO;

	@Mock
	private StockDAO stockDAO;

	@Mock
	private SaleItemDAO saleItemDAO;

	@Mock
	private InventoryDAO inventoryDAO;

	@Mock
	private PaymentService paymentService;

	private Sale sale;

	@Before
	public void before() throws BusinessException {
		this.sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);
	}

	@Test
	public void shouldCalculateTotalSale() {

		final BigDecimal totalSale = this.sale.getItems().stream().map(SaleItem::getTotalSaleItem)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.sale.calculateTotal();

		Assert.assertEquals(totalSale, this.sale.getTotal());
	}

	@Test
	public void shouldCalculateTotalProfitSale() {

		final BigDecimal totalProfit = this.sale.getItems().stream().map(SaleItem::getTotalBilling)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.sale.calculateBilling();

		Assert.assertEquals(totalProfit, this.sale.getBilling());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotCreateSaleWithoutItems() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test
	public void shouldRegistSale() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS, result -> {
			final List<SaleItem> services = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SERVICE);
			services.forEach(service -> {
				if (result instanceof Sale) {
					((Sale) result).addItem(service);
				}
			});
		});

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);

		Mockito.when(this.stockDAO.findByUuid(stock.getUuid())).thenReturn(stock);
		Mockito.when(this.inventoryDAO.fetchByGroceryAndStatus(sale.getGrocery(), InventoryStatus.PENDING, EntityStatus.ACTIVE))
		.thenThrow(new BusinessException("Inventory not found..."));

		this.saleService.registSale(this.getUserContext(), sale);

		final int compareTo = sale.getTotal().compareTo(BigDecimal.ZERO);

		Assert.assertTrue(compareTo > 0);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistSaleWithPendingInventory() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		Mockito.when(this.inventoryDAO.fetchByGroceryAndStatus(sale.getGrocery(), InventoryStatus.PENDING, EntityStatus.ACTIVE))
		.thenReturn(EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID));

		this.saleService.registSale(this.getUserContext(), sale);
	}
}
