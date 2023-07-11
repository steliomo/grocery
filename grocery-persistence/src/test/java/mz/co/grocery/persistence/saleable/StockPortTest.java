/**
 *
 */
package mz.co.grocery.persistence.saleable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.application.sale.in.SaleUseCase;
import mz.co.grocery.core.application.sale.out.StockPort;
import mz.co.grocery.core.application.sale.service.CashSaleService;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.core.domain.sale.StockStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.SaleTemplate;
import mz.co.grocery.persistence.fixturefactory.StockTemplate;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class StockPortTest extends AbstractIntegServiceTest {

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort porProductUnitPort;

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private StockPort stockPort;

	@Inject
	private UnitPort unitPort;

	@Inject
	@BeanQualifier(CashSaleService.NAME)
	private SaleUseCase saleService;

	@Inject
	private StockBuilder stockBuilder;

	private String stockUuid;

	private Product product;

	private Unit unit;

	@Before
	public void before() throws BusinessException {

		this.unit = this.unitPort.createUnit(this.getUserContext(),
				EntityFactory.gimme(Unit.class, UnitTemplate.VALID));

		final List<Stock> stocks = this.stockBuilder.quantity(10).unit(this.unit).valid().build();

		stocks.forEach(stock -> {
			this.product = stock.getProductDescription().get().getProduct().get();
			this.stockUuid = stock.getUuid();
		});
	}

	@Test
	public void shouldFetchAllStocks() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<Stock> stocks = this.stockPort.fetchAllStocks(currentPage, maxResult);

		Assert.assertFalse(stocks.isEmpty());
		Assert.assertEquals(maxResult, stocks.size());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().get().getProduct());
			Assert.assertNotNull(stock.getProductDescription().get().getProductUnit());
		});
	}

	@Test
	public void shouldFectStockByUuid() throws BusinessException {
		final Stock stock = this.stockPort.fetchStockByUuid(this.stockUuid);

		Assert.assertNotNull(stock.getProductDescription());
		Assert.assertNotNull(stock.getProductDescription().get().getProduct());
		Assert.assertNotNull(stock.getProductDescription().get().getProductUnit());
	}

	@Test
	public void shouldFetchStocksByProductDescription() throws BusinessException {

		final String description = "milho";

		final List<Stock> stocks = this.stockPort.fetchStocksByProductDescription(description);

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().get().getProduct());
			Assert.assertNotNull(stock.getProductDescription().get().getProductUnit());
		});
	}

	@Test
	public void shouldFecthStockByProduct() throws BusinessException {
		final List<Stock> stocks = this.stockPort.fetchStockByGroceryAndProduct(this.unit.getUuid(),
				this.product.getUuid());

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription().get().getProduct());
			Assert.assertEquals(this.product.getUuid(), stock.getProductDescription().get().getProduct().get().getUuid());
		});
	}

	@Test
	public void shouldFecthStocksByGrocery() throws BusinessException {

		final List<Stock> stocks = this.stockPort.fetchStocksByGrocery(this.unit.getUuid());

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().get().getProduct());
			Assert.assertNotNull(stock.getProductDescription().get().getProductUnit());
		});
	}

	@Test
	public void shouldFetchLowStockByGroceryAndSalePeriod() throws BusinessException {

		Stock stock = EntityFactory.gimme(Stock.class, StockTemplate.VALID);
		stock.setUnit(this.unit);

		ProductDescription productDescription = stock.getProductDescription().get();

		final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());

		final ProductUnit productUnit = this.porProductUnitPort.createProductUnit(this.getUserContext(),
				productDescription.getProductUnit().get());

		productDescription.setProduct(product);
		productDescription.setProductUnit(productUnit);

		productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(),
				productDescription);

		stock.setProductDescription(productDescription);
		stock.setStockStatus();

		stock = this.stockPort.createStock(this.getUserContext(), stock);

		final SaleItem saleItem = new SaleItem();
		saleItem.setStock(stock);
		saleItem.setQuantity(stock.getQuantity());
		saleItem.setSaleItemValue(BigDecimal.ZERO);
		saleItem.setDiscount(BigDecimal.ZERO);

		final Sale sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);
		sale.setUnit(stock.getUnit().get());
		sale.addItem(saleItem);
		sale.setSaleType(SaleType.CASH);

		this.saleService.registSale(this.getUserContext(), sale);

		final LocalDate startDate = LocalDate.now();
		final LocalDate endDate = LocalDate.now();

		final List<Stock> stocks = this.stockPort.fetchLowStocksByGroceryAndSalePeriod(
				stock.getUnit().get().getUuid(), startDate,
				endDate);

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(st -> {
			Assert.assertEquals(StockStatus.LOW, st.getStockStatus());
			Assert.assertNotNull(st.getProductDescription());
			Assert.assertNotNull(st.getProductDescription().get().getProduct());
			Assert.assertNotNull(st.getProductDescription().get().getProductUnit());
		});
	}

	@Test
	public void shouldFecthStockNotInThisGroceryByProduct() throws BusinessException {
		final Unit grocery = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		this.unitPort.createUnit(this.getUserContext(), grocery);

		final List<Stock> stocks = this.stockPort.fetchStockNotInthisGroceryByProduct(grocery.getUuid(),
				this.product.getUuid());

		Assert.assertFalse(stocks.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotNull(stock.getProductDescription().get().getProduct());
			Assert.assertEquals(this.product.getUuid(), stock.getProductDescription().get().getProduct().get().getUuid());
			Assert.assertNotEquals(grocery.getUuid(), stock.getUnit().get().getUuid());
		});
	}

	@Test
	public void shouldFindStocksInAnalysis() throws BusinessException {
		this.stockBuilder.quantity(10).unit(this.unit).inAnalysis().build();
		final List<Stock> stocksInAnalysis = this.stockPort.fetchStocksInAnalysisByUnit(this.unit.getUuid());

		Assert.assertFalse(stocksInAnalysis.isEmpty());

		stocksInAnalysis.forEach(stock -> {
			Assert.assertEquals(StockStatus.BAD, stock.getProductStockStatus());
			Assert.assertNotNull(stock.getProductDescription());
			Assert.assertNotNull(stock.getProductDescription().get().getProduct());
			Assert.assertNotNull(stock.getProductDescription().get().getProductUnit());
		});
	}
}
