/**
 *
 */
package mz.co.grocery.core.sale.integ;

import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.core.sale.service.SaleQueryService;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.stock.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SaleQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private SaleService saleService;

	@Inject
	private StockService stockService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private SaleQueryService saleQueryService;

	@Before
	public void before() throws BusinessException {
		final Sale sale = new Sale();
		sale.setSaleDate(LocalDate.now());

		final List<SaleItem> saleItems = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.VALID);
		final List<SaleItem> saleItemsValues = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SALE_VALUE);

		saleItems.forEach(saleItem -> {

			try {
				this.productService.createProduct(this.getUserContext(),
				        saleItem.getStock().getProductDescription().getProduct());
				this.productSizeService.createProductUnit(this.getUserContext(),
				        saleItem.getStock().getProductDescription().getProductUnit());
				this.productDescriptionService.createProductDescription(this.getUserContext(),
				        saleItem.getStock().getProductDescription());
				this.stockService.createStock(this.getUserContext(), saleItem.getStock());
			}
			catch (final BusinessException e) {
				e.printStackTrace();
			}

			sale.addItem(saleItem);
		});

		saleItemsValues.forEach(saleItem -> {

			try {
				this.productService.createProduct(this.getUserContext(),
				        saleItem.getStock().getProductDescription().getProduct());
				this.productSizeService.createProductUnit(this.getUserContext(),
				        saleItem.getStock().getProductDescription().getProductUnit());
				this.productDescriptionService.createProductDescription(this.getUserContext(),
				        saleItem.getStock().getProductDescription());
				this.stockService.createStock(this.getUserContext(), saleItem.getStock());
			}
			catch (final BusinessException e) {
				e.printStackTrace();
			}

			sale.addItem(saleItem);
		});

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test
	public void shouldFindLast7DaysSale() throws BusinessException {
		final List<SaleReport> sales = this.saleQueryService.findLast7DaysSale();
		assertFalse(sales.isEmpty());
	}

	@Test
	public void shouldFindSalePerPeriod() throws BusinessException {
		final List<SaleReport> sales = this.saleQueryService.findSalesPerPeriod(LocalDate.now(), LocalDate.now());
		assertFalse(sales.isEmpty());
	}
}
