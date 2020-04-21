/**
 *
 */
package mz.co.grocery.core.stock.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.stock.dao.StockDAO;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.model.StockStatus;
import mz.co.grocery.core.stock.service.StockService;
import mz.co.grocery.core.stock.service.StockServiceImpl;
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

	private Stock stock;

	@Before
	public void before() {
		this.stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
	}

	@Test
	public void shouldRemoveStock() throws BusinessException {
		this.stockService.removeStock(this.getUserContext(), this.stock);
		assertEquals(EntityStatus.INACTIVE, this.stock.getEntityStatus());
	}

	@Test
	public void shouldUpdateStockAndPrices() throws BusinessException {
		when(this.stockDAO.findByUuid(this.stock.getUuid())).thenReturn(this.stock);

		final BigDecimal purchasePrice = this.stock.getPurchasePrice();
		final BigDecimal salePrice = this.stock.getSalePrice();
		final BigDecimal quantity = this.stock.getQuantity();

		this.stockService.updateStocksAndPrices(this.getUserContext(), this.stock);

		assertEquals(purchasePrice, this.stock.getPurchasePrice());
		assertEquals(salePrice, this.stock.getSalePrice());
		assertEquals(quantity.multiply(new BigDecimal(2)), this.stock.getQuantity());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockAndPricesWithQuantityZero() throws BusinessException {
		this.stock.setQuantity(BigDecimal.ZERO);

		this.stockService.updateStocksAndPrices(this.getUserContext(), this.stock);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockAndPricesWithZeroPrices() throws BusinessException {
		this.stock.setPurchasePrice(BigDecimal.ZERO);
		this.stock.setSalePrice(BigDecimal.ZERO);

		this.stockService.updateStocksAndPrices(this.getUserContext(), this.stock);
	}

	@Test
	public void shouldSetMinimumStock() {

		BigDecimal minimumStock = new BigDecimal(10);
		stock.setMinimumStock(minimumStock);

		assertEquals(minimumStock, stock.getMinimumStock());
		assertEquals(StockStatus.GOOD, stock.getStockStatus());
	}
}
