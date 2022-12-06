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
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.item.service.ServiceDescriptionService;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.grocery.core.sale.service.SaleQueryService;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.saleable.service.ServiceItemService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.core.util.ApplicationTranslator;
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

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceItemService serviceItemService;

	@Mock
	private ApplicationTranslator translator;

	private Grocery grocery;

	@Before
	public void before() throws BusinessException {
		final Sale sale = new Sale();
		sale.setSaleDate(LocalDate.now());

		final List<SaleItem> products = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.PRODUCT);
		final List<SaleItem> services = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SERVICE);

		products.forEach(saleItem -> {

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

		services.forEach(service -> {

			try {
				this.serviceService.createService(this.getUserContext(), service.getServiceItem().getServiceDescription().getService());
				this.serviceDescriptionService.createServiceDescription(this.getUserContext(), service.getServiceItem().getServiceDescription());
				this.groceryService.createGrocery(this.getUserContext(), service.getServiceItem().getUnit());
				this.serviceItemService.createServiceItem(this.getUserContext(), service.getServiceItem());

			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			sale.addItem(service);
		});

		this.grocery = this.groceryService.createGrocery(this.getUserContext(),
				EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));
		sale.setGrocery(this.grocery);
		sale.setSaleType(SaleType.CASH);

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
