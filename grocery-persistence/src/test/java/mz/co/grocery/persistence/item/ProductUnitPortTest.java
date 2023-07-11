/**
 *
 */
package mz.co.grocery.persistence.item;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ProductUnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitPortTest extends AbstractIntegServiceTest {

	@Inject
	private ProductUnitPort productUnitPort;

	@Before
	public void before() {
		final List<ProductUnit> productUnits = EntityFactory.gimme(ProductUnit.class, 10, ProductUnitTemplate.VALID);
		productUnits.forEach(productUnit -> {
			this.createProductUnit(productUnit);
		});
	}

	private void createProductUnit(final ProductUnit productUnit) {
		try {
			this.productUnitPort.createProductUnit(this.getUserContext(), productUnit);
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFindAllProdutUnits() throws BusinessException {
		final List<ProductUnit> productUnits = this.productUnitPort.findAllProductUnits();
		Assert.assertFalse(productUnits.isEmpty());
	}
}
