/**
 *
 */
package mz.co.grocery.persistence.item;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.item.model.Product;
import mz.co.grocery.core.item.service.ProductService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ProductTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ProductServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ProductService productService;

	private Product product;

	@Before
	public void before() {
		this.product = EntityFactory.gimme(Product.class, ProductTemplate.VALID);
	}

	@Test
	public void shouldCreateProduct() throws BusinessException {

		this.productService.createProduct(this.getUserContext(), this.product);

		TestUtil.assertCreation(this.product);
	}

	@Test
	public void shouldUpdateProduct() throws BusinessException {
		this.productService.createProduct(this.getUserContext(), this.product);
		this.productService.uppdateProduct(this.getUserContext(), this.product);

		TestUtil.assertUpdate(this.product);
	}
}
