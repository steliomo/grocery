/**
 *
 */
package mz.co.grocery.core.sale.integ;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.SaleItemTemplate;
import mz.co.grocery.core.fixturefactory.SaleTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.sale.service.SaleService;
import mz.co.grocery.core.stock.service.StockService;
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
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private GroceryService groceryService;

	private Sale sale;

	@Before
	public void before() throws BusinessException {

		this.sale = EntityFactory.gimme(Sale.class, SaleTemplate.VALID);
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
			}
			catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(saleItem);
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
			}
			catch (final BusinessException e) {
				e.printStackTrace();
			}

			this.sale.addItem(saleItem);
		});

		this.groceryService.createGrocery(this.getUserContext(), this.sale.getGrocery());
	}

	@Test
	public void shouldRegisteSale() throws BusinessException {

		this.saleService.registSale(this.getUserContext(), this.sale);
		final int compareTo = this.sale.getTotal().compareTo(BigDecimal.ZERO);
		assertTrue(compareTo > 0);

		TestUtil.assertCreation(this.sale);
	}
}
