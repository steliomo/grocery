/**
 *
 */
package mz.co.grocery.core.item.integ;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ProductUnitTemplate;
import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.grocery.core.item.service.ProductUnitQueryService;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitQueryServiveTest extends AbstractIntegServiceTest {

	@Inject
	private ProductUnitService productUnitService;

	@Inject
	private ProductUnitQueryService productUnitQueryService;

	@Before
	public void before() {
		final List<ProductUnit> productUnits = EntityFactory.gimme(ProductUnit.class, 10, ProductUnitTemplate.VALID);
		productUnits.forEach(productUnit -> {
			this.createProductUnit(productUnit);
		});
	}

	private void createProductUnit(final ProductUnit productUnit) {
		try {
			this.productUnitService.createProductUnit(this.getUserContext(), productUnit);
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFindAllProdutUnits() throws BusinessException {
		final List<ProductUnit> productUnits = this.productUnitQueryService.findAllProductUnits();
		assertFalse(productUnits.isEmpty());
	}
}
