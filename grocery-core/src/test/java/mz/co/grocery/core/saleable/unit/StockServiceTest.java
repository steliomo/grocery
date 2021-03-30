/**
 *
 */
package mz.co.grocery.core.saleable.unit;

import java.math.BigDecimal;

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
}
