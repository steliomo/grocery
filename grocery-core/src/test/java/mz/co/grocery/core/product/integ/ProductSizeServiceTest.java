/**
 *
 */
package mz.co.grocery.core.product.integ;

import javax.inject.Inject;

import org.junit.Test;

import mz.co.grocery.core.config.AbstractServiceTest;
import mz.co.grocery.core.fixturefactory.ProductSizeTemplate;
import mz.co.grocery.core.product.model.ProductSize;
import mz.co.grocery.core.product.service.ProductSizeService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ProductSizeServiceTest extends AbstractServiceTest {

	@Inject
	private ProductSizeService productSizeService;

	@Test
	public void shouldCreateProductSize() throws BusinessException {
		final ProductSize productSize = EntityFactory.gimme(ProductSize.class, ProductSizeTemplate.VALID);

		this.productSizeService.createProductSize(this.getUserContext(), productSize);

		TestUtil.assertCreation(productSize);
	}
}
