/**
 *
 */
package mz.co.grocery.core.saleable.integ;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.model.Product;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.service.CashSaleServiceImpl;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.saleable.builder.StockBuilder;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.model.StockStatus;
import mz.co.grocery.core.saleable.service.StockQueryService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class StockQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private StockQueryService stockQueryService;

	@Inject
	private StockService stockService;

	@Inject
	private GroceryService groceryService;

	@Inject
	@Qualifier(CashSaleServiceImpl.NAME)
	private SaleService saleService;

	@Inject
	private StockBuilder stockBuilder;

	private String stockUuid;

	private Product product;

	private Grocery unit;

	@Before
	public void before() throws BusinessException {

		this.unit = this.groceryService.createGrocery(this.getUserContext(),
				EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		final List<Stock> stocks = this.stockBuilder.quantity(10).unit(this.unit).valid().build();

		stocks.forEach(stock -> {
			this.product = stock.getProductDescription().getProduct();
			this.stockUuid = stock.getUuid();
		});
	}

	@Test
	public void shouldFetchAllStocks() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<Stock> stocks = this.stockQueryService.fetchAllStocks(currentPage, maxResult);

		Assert.assertFalse(stocks.isEmpty());
		Assert.assertEquals(maxResult, stocks.size());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().getProduct());
			Assert.assertNotNull(stock.getProductDescription().getProductUnit());
		});
	}

	@Test
	public void shouldFectStockByUuid() throws BusinessException {
		final Stock stock = this.stockQueryService.fetchStockByUuid(this.stockUuid);

		Assert.assertNotNull(stock.getProductDescription());
		Assert.assertNotNull(stock.getProductDescription().getProduct());
		Assert.assertNotNull(stock.getProductDescription().getProductUnit());
	}

	@Test
	public void shouldFetchStocksByProductDescription() throws BusinessException {

		final String description = "milho";

		final List<Stock> stocks = this.stockQueryService.fetchStocksByProductDescription(description);

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().getProduct());
			Assert.assertNotNull(stock.getProductDescription().getProductUnit());
		});
	}

	@Test
	public void shouldFecthStockByProduct() throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchStockByGroceryAndProduct(this.unit.getUuid(),
				this.product.getUuid());

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription().getProduct());
			Assert.assertEquals(this.product.getUuid(), stock.getProductDescription().getProduct().getUuid());
		});
	}

	@Test
	public void shouldFecthStocksByGrocery() throws BusinessException {

		final List<Stock> stocks = this.stockQueryService.fetchStocksByGrocery(this.unit.getUuid());

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().getProduct());
			Assert.assertNotNull(stock.getProductDescription().getProductUnit());
		});
	}

	@Test
	public void shouldFetchLowStockByGroceryAndSalePeriod() throws BusinessException {

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		stock.setGrocery(this.unit);

		this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());
		this.productUnitService.createProductUnit(this.getUserContext(),
				stock.getProductDescription().getProductUnit());
		this.productDescriptionService.createProductDescription(this.getUserContext(),
				stock.getProductDescription());

		this.stockService.createStock(this.getUserContext(), stock);

		final SaleItem saleItem = new SaleItem();
		saleItem.setStock(stock);
		saleItem.setQuantity(stock.getQuantity());

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);
		sale.setGrocery(stock.getGrocery());
		sale.addItem(saleItem);
		sale.setSaleType(SaleType.CASH);

		this.saleService.registSale(this.getUserContext(), sale);

		final LocalDate startDate = LocalDate.now();
		final LocalDate endDate = LocalDate.now();

		final List<Stock> stocks = this.stockQueryService.fetchLowStocksByGroceryAndSalePeriod(
				stock.getGrocery().getUuid(), startDate,
				endDate);

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(st -> {
			Assert.assertEquals(StockStatus.LOW, st.getStockStatus());
			Assert.assertNotNull(st.getProductDescription());
			Assert.assertNotNull(st.getProductDescription().getProduct());
			Assert.assertNotNull(st.getProductDescription().getProductUnit());
		});
	}

	@Test
	public void shouldFecthStockNotInThisGroceryByProduct() throws BusinessException {
		final Grocery grocery = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);
		this.groceryService.createGrocery(this.getUserContext(), grocery);

		final List<Stock> stocks = this.stockQueryService.fetchStockNotInthisGroceryByProduct(grocery.getUuid(),
				this.product.getUuid());

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription().getProduct());
			Assert.assertEquals(this.product.getUuid(), stock.getProductDescription().getProduct().getUuid());
			Assert.assertNotEquals(grocery.getUuid(), stock.getGrocery().getUuid());
		});
	}

	@Test
	public void shouldFindStocksInAnalysis() throws BusinessException {
		this.stockBuilder.quantity(10).unit(this.unit).inAnalysis().build();
		final List<Stock> stocksInAnalysis = this.stockQueryService.fetchStocksInAnalysisByUnit(this.unit.getUuid());

		Assert.assertFalse(stocksInAnalysis.isEmpty());

		stocksInAnalysis.forEach(stock -> {
			Assert.assertEquals(StockStatus.BAD, stock.getProductStockStatus());
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().getProduct());
			Assert.assertNotNull(stock.getProductDescription().getProductUnit());
		});

	}
}
