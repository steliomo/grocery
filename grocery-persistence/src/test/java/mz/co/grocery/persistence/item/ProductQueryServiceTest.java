/**
 *
 */
package mz.co.grocery.persistence.item;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.item.model.Product;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductQueryService;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.service.StockService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.GroceryTemplate;
import mz.co.grocery.persistence.fixturefactory.ProductTemplate;
import mz.co.grocery.persistence.fixturefactory.StockTemplate;
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
			} catch (final BusinessException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void shouldFindAllProducts() throws BusinessException {
		final List<Product> products = this.productQueryService.findAllProducts();
		Assert.assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductByName() throws BusinessException {

		final List<Product> products = this.productQueryService.findProductByName(this.products.get(0).getName());

		Assert.assertFalse(products.isEmpty());
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

		Assert.assertFalse(products.isEmpty());
	}

	@Test
	public void shouldFindProductsNotInThisGrocery() throws BusinessException {

		final Grocery grocery = EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID);
		this.groceryService.createGrocery(this.getUserContext(), grocery);

		final List<Stock> stocks = EntityFactory.gimme(Stock.class, 10, StockTemplate.VALID);
		this.grocery = this.groceryService.createGrocery(this.getUserContext(),
				EntityFactory.gimme(Grocery.class, GroceryTemplate.VALID));

		stocks.forEach(stock -> {
			this.createStock(stock);
		});

		final List<Product> products = this.productQueryService.findProductsNotInThisGrocery(grocery);

		Assert.assertFalse(products.isEmpty());

		stocks.forEach(stock -> {
			Assert.assertNotEquals(grocery.getUuid(), stock.getGrocery().getUuid());
		});
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
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}
}
