/**
 *
 */
package mz.co.grocery.core.product.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ProductDescriptionTemplate;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productSizeService;

	@Inject
	private ProductDescriptionService productDescriptionService;

	private ProductDescription productDescription;

	@Before
	public void before() throws BusinessException {
		this.productDescription = EntityFactory.gimme(ProductDescription.class, ProductDescriptionTemplate.VALID);

		this.productService.createProduct(this.getUserContext(), this.productDescription.getProduct());
		this.productSizeService.createProductUnit(this.getUserContext(), this.productDescription.getProductUnit());

	}

	@Test
	public void shouldCreateProductDescription() throws BusinessException {

		this.productDescriptionService.createProductDescription(this.getUserContext(), this.productDescription);

		TestUtil.assertCreation(this.productDescription);
	}
}
