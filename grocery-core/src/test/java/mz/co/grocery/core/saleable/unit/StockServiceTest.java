/**
 *
 */
package mz.co.grocery.core.saleable.unit;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.saleable.dao.StockDAO;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.model.StockStatus;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.core.saleable.service.StockServiceImpl;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public class StockServiceTest extends AbstractUnitServiceTest {

	@Mock
	private StockDAO stockDAO;

	@InjectMocks
	private final StockService stockService = new StockServiceImpl();

	@Mock
	private ApplicationTranslator translator;

	private Stock stock;

	@Before
	public void before() {
		this.stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
	}

	@Test
	public void shouldRemoveStock() throws BusinessException {
		this.stockService.removeStock(this.getUserContext(), this.stock);
		Assert.assertEquals(EntityStatus.INACTIVE, this.stock.getEntityStatus());
	}

	@Test
	public void shouldUpdateStockAndPrices() throws BusinessException {
		Mockito.when(this.stockDAO.findByUuid(this.stock.getUuid())).thenReturn(this.stock);

		final BigDecimal purchasePrice = this.stock.getPurchasePrice();
		final BigDecimal salePrice = this.stock.getSalePrice();
		final BigDecimal quantity = this.stock.getQuantity();

		this.stockService.updateStocksAndPrices(this.getUserContext(), this.stock);

		Assert.assertEquals(purchasePrice, this.stock.getPurchasePrice());
		Assert.assertEquals(salePrice, this.stock.getSalePrice());
		Assert.assertEquals(quantity.multiply(new BigDecimal(2)), this.stock.getQuantity());

		Assert.assertNotNull(this.stock.getStockUpdateDate());
		Assert.assertNotNull(this.stock.getStockUpdateQuantity());
		Assert.assertEquals(this.stock.getStockUpdateQuantity(), this.stock.getQuantity());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockAndPricesWithZeroPrices() throws BusinessException {
		this.stock.setPurchasePrice(BigDecimal.ZERO);
		this.stock.setSalePrice(BigDecimal.ZERO);

		this.stockService.updateStocksAndPrices(this.getUserContext(), this.stock);
	}

	@Test
	public void shouldSetMinimumStock() {

		final BigDecimal minimumStock = new BigDecimal(10);
		this.stock.setMinimumStock(minimumStock);

		Assert.assertEquals(minimumStock, this.stock.getMinimumStock());
		Assert.assertEquals(StockStatus.GOOD, this.stock.getStockStatus());
	}

	@Test
	public void shouldRegularizeStock() throws BusinessException {

		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.IN_ANALYSIS);
		Mockito.when(this.stockDAO.findByUuid(stock.getUuid())).thenReturn(stock);

		this.stockService.regularize(this.getUserContext(), stock);

		Assert.assertEquals(stock.getQuantity(), stock.getInventoryQuantity());
		Assert.assertEquals(StockStatus.GOOD, stock.getStockStatus());
		Assert.assertEquals(stock.getStockUpdateQuantity(), stock.getQuantity());
		Assert.assertEquals(LocalDate.now(), stock.getStockUpdateDate());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockWithPurchasePriceGreaterThanSalePrice() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(10));

		this.stockService.updateStocksAndPrices(this.getUserContext(), stock);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockWithPurchasePriceEqualToSalePrice() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(100));

		this.stockService.updateStocksAndPrices(this.getUserContext(), stock);
	}

	@Test
	public void shouldAdjustNegativeQuantityValues() {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.IN_ANALYSIS);
		stock.setQuantity(BigDecimal.ZERO);

		stock.adjustNegativeQuantity();

		Assert.assertEquals(stock.getQuantity(), stock.getInventoryQuantity());
	}
}
