/**
 *
 */
package mz.co.grocery.core.item.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ProductUnitTemplate;
import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ProductUnitService productSizeService;

	@Before
	public void before() {
	}

	@Test
	public void shouldCreateProductUnit() throws BusinessException {
		final ProductUnit productUnit = EntityFactory.gimme(ProductUnit.class, ProductUnitTemplate.VALID);
		this.productSizeService.createProductUnit(this.getUserContext(), productUnit);

		TestUtil.assertCreation(productUnit);
	}

	@Test
	public void shouldUpdateProductUnit() throws BusinessException {
		final ProductUnit productUnit = EntityFactory.gimme(ProductUnit.class, ProductUnitTemplate.VALID);
		this.productSizeService.createProductUnit(this.getUserContext(), productUnit);
		this.productSizeService.updateProductUnit(this.getUserContext(), productUnit);

		TestUtil.assertUpdate(productUnit);
	}
}
