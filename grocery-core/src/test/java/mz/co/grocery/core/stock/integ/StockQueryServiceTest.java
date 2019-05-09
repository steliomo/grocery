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
import mz.co.grocery.core.fixturefactory.StockTemplate;
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

	@Before
	public void before() throws BusinessException {

		final List<Stock> stocks = EntityFactory.gimme(Stock.class, 10, StockTemplate.VALID);

		stocks.forEach(stock -> {
			this.createStock(stock);
		});
	}

	private void createStock(final Stock stock) {
		try {
			this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());
			this.productUnitService.createProductUnit(this.getUserContext(),
			        stock.getProductDescription().getProductUnit());
			this.productDescriptionService.createProductDescription(this.getUserContext(),
			        stock.getProductDescription());
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
}
