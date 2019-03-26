/**
 *
 */
package mz.co.grocery.core.product.integ;

import javax.inject.Inject;

import org.junit.Test;

import mz.co.grocery.core.config.AbstractServiceTest;
import mz.co.grocery.core.fixturefactory.ProductTemplate;
import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ProductServiceTest extends AbstractServiceTest {

	@Inject
	private ProductService productService;

	@Test
	public void shouldCreateProduct() throws BusinessException {

		final Product product = EntityFactory.gimme(Product.class, ProductTemplate.VALID);
		this.productService.createProduct(this.getUserContext(), product);

		TestUtil.assertCreation(product);
	}
}
