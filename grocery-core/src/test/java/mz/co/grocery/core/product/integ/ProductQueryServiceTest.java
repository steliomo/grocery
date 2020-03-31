/**
 *
 */
package mz.co.grocery.core.product.integ;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.GroceryTemplate;
import mz.co.grocery.core.fixturefactory.ProductTemplate;
import mz.co.grocery.core.fixturefactory.StockTemplate;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductQueryService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.grocery.core.stock.model.Stock;
import mz.co.grocery.core.stock.service.StockService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ProductService productService;

	@Inject
	private ProductQueryService productQueryService;

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private StockService stockService;

	@Inject
	private GroceryService groceryService;

	private List<Product> products;

	private Grocery grocery;

	@Before
	public void before() {
		this.products = EntityFactory.gimme(Product.class, 10, ProductTemplate.VALID);

		this.products.forEach(product -> {
			try {
				this.productService.createProduct(this.getUserContext(), product);
			}
			catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFindAllProducts() throws BusinessException {
		final List<Product> products = this.productQueryService.findAllProducts();
		assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductByName() throws BusinessException {

		final List<Product> products = this.productQueryService.findProductByName(this.products.get(0).getName());

		assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductByGrocery() throws BusinessException {

		final List<Stock> stocks = EntityFactory.gimme(Stock.class, 10, StockTemplate.VALID);
		this.grocery = this.groceryService.createGrocery(this.getUserContext(),
		        EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		stocks.forEach(stock -> {
			this.createStock(stock);
		});

		final List<Product> products = this.productQueryService.findProductsByGrocery(this.grocery);

		assertFalse(products.isEmpty());
	}

	private void createStock(final Stock stock) {
		try {
			this.productService.createProduct(this.getUserContext(), stock.getProductDescription().getProduct());

			this.productUnitService.createProductUnit(this.getUserContext(),
			        stock.getProductDescription().getProductUnit());
			this.productDescriptionService.createProductDescription(this.getUserContext(),
			        stock.getProductDescription());
			stock.setGrocery(this.grocery);
			this.stockService.createStock(this.getUserContext(), stock);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}
}
