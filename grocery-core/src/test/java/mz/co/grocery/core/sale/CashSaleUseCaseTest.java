/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.application.payment.in.SubscriptionUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.sale.service.CashSaleService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.fixturefactory.InventoryTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class CashSaleUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private SalePort salePort;

	@Mock
	private StockPort stockPort;

	@Mock
	private SaleItemPort saleItemPort;

	@Mock
	private SubscriptionUseCase paymentUseCase;

	@Mock
	private InventoryPort inventoryPort;

	@InjectMocks
	private CashSaleService saleUseCase;

	private Sale sale;

	@Before
	public void before() throws BusinessException {
		this.sale = EntityFactory.gimme(Sale.class, SaleTemplate.WITH_ITEMS);
	}

	@Test
	public void shouldCalculateTotalSale() {

		final BigDecimal totalSale = this.sale.getItems().get().stream().map(SaleItem::getTotalSaleItem)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.sale.calculateTotal();

		Assert.assertEquals(totalSale, this.sale.getTotal());
	}

	@Test
	public void shouldCalculateTotalProfitSale() {

		final BigDecimal totalProfit = this.sale.getItems().get().stream().map(SaleItem::getTotalBilling)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.sale.calculateBilling();

		Assert.assertEquals(totalProfit, this.sale.getBilling());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotCreateSaleWithoutItems() throws BusinessException {
		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		this.saleUseCase.registSale(this.getUserContext(), sale);
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

		Mockito.when(this.salePort.createSale(ArgumentMatchers.any(UserContext.class), ArgumentMatchers.any(Sale.class))).thenReturn(sale);

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);

		Mockito.when(this.stockPort.findStockByUuid(stock.getUuid())).thenReturn(stock);
		Mockito.when(this.inventoryPort.fetchInventoryByGroceryAndStatus(sale.getUnit().get(), InventoryStatus.PENDING))
		.thenThrow(new BusinessException("Inventory not found..."));

		sale.setSaleType(SaleType.CASH);

		this.saleUseCase.registSale(this.getUserContext(), sale);

		final int compareTo = sale.getTotal().compareTo(BigDecimal.ZERO);

		Assert.assertTrue(compareTo > 0);
		Assert.assertEquals(DeliveryStatus.NA, sale.getDeliveryStatus());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotRegistSaleWithPendingInventory() throws BusinessException {

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);

		Mockito.when(this.inventoryPort.fetchInventoryByGroceryAndStatus(sale.getUnit().get(), InventoryStatus.PENDING))
		.thenReturn(EntityFactory.gimme(Inventory.class, InventoryTemplate.VALID));

		this.saleUseCase.registSale(this.getUserContext(), sale);
	}
}
