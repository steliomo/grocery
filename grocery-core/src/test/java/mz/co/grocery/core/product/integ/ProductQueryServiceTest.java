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
import mz.co.grocery.core.fixturefactory.ProductTemplate;
import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.service.ProductQueryService;
import mz.co.grocery.core.product.service.ProductService;
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

	private List<Product> products;

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
}
