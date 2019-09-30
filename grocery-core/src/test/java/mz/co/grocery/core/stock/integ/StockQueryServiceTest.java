/**
 *
 */
package mz.co.grocery.core.stock.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.service.StockQueryService;
import mz.co.grocery.core.stock.service.StockService;
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

	private String stockUuid;

	private Product product;

	private Grocery grocery;

	@Before
	public void before() throws BusinessException {

		final List<Stock> stocks = EntityFactory.gimme(Stock.class, 10, StockTemplate.VALID);
		this.grocery = this.groceryService.createGrocery(this.getUserContext(),
		        EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		stocks.forEach(stock -> {
			this.createStock(stock);
			this.stockUuid = stock.getUuid();
		});
	}

	private void createStock(final Stock stock) {
		try {
			this.product = stock.getProductDescription().getProduct();

			this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());
			this.productUnitService.createProductUnit(this.getUserContext(),
			        stock.getProductDescription().getProductUnit());
			this.productDescriptionService.createProductDescription(this.getUserContext(),
			        stock.getProductDescription());
			stock.setGrocery(this.grocery);
			this.stockService.createStock(this.getUserContext(), stock);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFetchAllStocks() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<Stock> stocks = this.stockQueryService.fetchAllStocks(currentPage, maxResult);

		assertFalse(stocks.isEmpty());
		assertEquals(maxResult, stocks.size());

		stocks.forEach(stock -> {
			assertNotNull(stock.getProductDescription());
			assertNotNull(stock.getProductDescription().getProduct());
			assertNotNull(stock.getProductDescription().getProductUnit());
		});
	}

	@Test
	public void shouldFectStockByUuid() throws BusinessException {
		final Stock stock = this.stockQueryService.fetchStockByUuid(this.stockUuid);

		assertNotNull(stock.getProductDescription());
		assertNotNull(stock.getProductDescription().getProduct());
		assertNotNull(stock.getProductDescription().getProductUnit());
	}

	@Test
	public void shouldFetchStocksByProductDescription() throws BusinessException {

		final String description = "milho";

		final List<Stock> stocks = this.stockQueryService.fetchStocksByProductDescription(description);

		assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			assertNotNull(stock.getProductDescription());
			assertNotNull(stock.getProductDescription().getProduct());
			assertNotNull(stock.getProductDescription().getProductUnit());
		});
	}

	@Test
	public void shouldFecthStockByProduct() throws BusinessException {
		final List<Stock> stocks = this.stockQueryService.fetchStockByGroceryAndProduct(this.grocery.getUuid(),
		        this.product.getUuid());

		assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			assertNotNull(stock.getProductDescription().getProduct());
			assertEquals(this.product.getUuid(), stock.getProductDescription().getProduct().getUuid());
		});
	}

	@Test
	public void shouldFecthStocksByGrocery() throws BusinessException {

		final List<Stock> stocks = this.stockQueryService.fetchStocksByGrocery(this.grocery.getUuid());

		assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			assertNotNull(stock.getProductDescription());
			assertNotNull(stock.getProductDescription().getProduct());
			assertNotNull(stock.getProductDescription().getProductUnit());
		});
	}
}
