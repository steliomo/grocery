/**
 *
 */
package mz.co.grocery.core.sale.integ;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.item.service.ServiceDescriptionService;
import mz.co.grocery.core.item.service.ServiceService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.saleable.service.ServiceItemService;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class SaleServiceTest extends AbstractIntegServiceTest {

	@Inject
	private SaleService saleService;

	@Inject
	private StockService stockService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceItemService serviceItemService;

	private Sale sale;

	@Before
	public void before() throws BusinessException {

		this.sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);
		final List<SaleItem> products = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.PRODUCT);
		final List<SaleItem> services = EntityFactory.gimme(SaleItem.class, 10, SaleItemTemplate.SERVICE);

		products.forEach(product -> {

			try {
				this.productService.createProduct(this.getUserContext(), product.getStock().getProductDescription().getProduct());
				this.productUnitService.createProductUnit(this.getUserContext(), product.getStock().getProductDescription().getProductUnit());
				this.productDescriptionService.createProductDescription(this.getUserContext(), product.getStock().getProductDescription());
				this.groceryService.createGrocery(this.getUserContext(), product.getStock().getGrocery());
				this.stockService.createStock(this.getUserContext(), product.getStock());
			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(product);
		});

		services.forEach(service -> {

			try {
				this.groceryService.createGrocery(this.getUserContext(), service.getServiceItem().getUnit());
				this.serviceService.createService(this.getUserContext(), service.getServiceItem().getServiceDescription().getService());
				this.serviceDescriptionService.createServiceDescription(this.getUserContext(), service.getServiceItem().getServiceDescription());
				this.serviceItemService.createServiceItem(this.getUserContext(), service.getServiceItem());

			} catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(service);
		});

		this.groceryService.createGrocery(this.getUserContext(), this.sale.getGrocery());
	}

	@Test
	public void shouldRegisteSale() throws BusinessException {
		this.sale.setSaleType(SaleType.CASH);

		this.saleService.registSale(this.getUserContext(), this.sale);
		final int compareTo = this.sale.getTotal().compareTo(BigDecimal.ZERO);
		Assert.assertTrue(compareTo > 0);

		TestUtil.assertCreation(this.sale);
	}
}
