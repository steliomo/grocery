/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.sale.in.UpdateStockAndPricesUseCase;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.sale.service.UpdateStockAndPricesService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class StockServiceTest extends AbstractUnitServiceTest {

	@Mock
	private StockPort stockPort;

	@InjectMocks
	private final UpdateStockAndPricesUseCase updateStockAndPricesUseCase = new UpdateStockAndPricesService(this.stockPort);

	@Mock
	private ApplicationTranslator translator;

	private Stock stock;

	@Before
	public void before() {
		this.stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
	}

	@Test
	public void shouldUpdateStockAndPrices() throws BusinessException {
		Mockito.when(this.stockPort.findStockByUuid(this.stock.getUuid())).thenReturn(this.stock);

		final BigDecimal purchasePrice = this.stock.getPurchasePrice();
		final BigDecimal salePrice = this.stock.getSalePrice();
		final BigDecimal quantity = this.stock.getQuantity();

		this.updateStockAndPricesUseCase.updateStocksAndPrices(this.getUserContext(), this.stock);

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

		this.updateStockAndPricesUseCase.updateStocksAndPrices(this.getUserContext(), this.stock);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockWithPurchasePriceGreaterThanSalePrice() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(10));

		this.updateStockAndPricesUseCase.updateStocksAndPrices(this.getUserContext(), stock);
	}

	@Test(expected = BusinessException.class)
	public void shouldNotUpdateStockWithPurchasePriceEqualToSalePrice() throws BusinessException {
		final Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		stock.setPurchasePrice(new BigDecimal(100));
		stock.setSalePrice(new BigDecimal(100));

		this.updateStockAndPricesUseCase.updateStocksAndPrices(this.getUserContext(), stock);
	}
}
