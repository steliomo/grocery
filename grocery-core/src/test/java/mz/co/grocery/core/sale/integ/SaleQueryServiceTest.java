/**
 *
 */
package mz.co.grocery.core.sale.integ;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
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

	@Inject
	private GroceryService groceryService;

	private Grocery grocery;

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
				this.groceryService.createGrocery(this.getUserContext(), saleItem.getStock().getGrocery());
				this.stockService.createStock(this.getUserContext(), saleItem.getStock());
			} catch (final BusinessException e) {
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
				this.groceryService.createGrocery(this.getUserContext(), saleItem.getStock().getGrocery());
				this.stockService.createStock(this.getUserContext(), saleItem.getStock());
			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			sale.addItem(saleItem);
		});

		this.grocery = this.groceryService.createGrocery(this.getUserContext(),
				EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));
		sale.setGrocery(this.grocery);

		this.saleService.registSale(this.getUserContext(), sale);
	}

	@Test
	public void shouldFindSalePerPeriod() throws BusinessException {
		final List<SaleReport> sales = this.saleQueryService.findSalesPerPeriod(this.grocery.getUuid(), LocalDate.now(),
				LocalDate.now());
		Assert.assertFalse(sales.isEmpty());
	}

	@Test
	public void shouldFindMonthlySalePerPeriod() throws BusinessException {

		final List<SaleReport> sales = this.saleQueryService.findMonthlySalesPerPeriod(this.grocery.getUuid(),
				LocalDate.now(),
				LocalDate.now());

		Assert.assertFalse(sales.isEmpty());
		Assert.assertEquals(1, sales.size());
	}
}
