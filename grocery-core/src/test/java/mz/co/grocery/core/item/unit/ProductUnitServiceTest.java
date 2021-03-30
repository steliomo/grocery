/**
 *
 */
package mz.co.grocery.core.item.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.ProductUnitTemplate;
import mz.co.grocery.core.item.dao.ProductUnitDAO;
import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.grocery.core.item.service.ProductUnitService;
import mz.co.grocery.core.item.service.ProductUnitServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public class ProductUnitServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ProductUnitDAO productUnitDAO;

	@InjectMocks
	private final ProductUnitService productUnitService = new ProductUnitServiceImpl();

	@Test
	public void shouldRemoveProductUnit() throws BusinessException {
		final ProductUnit productUnit = EntityFactory.gimme(ProductUnit.class, ProductUnitTemplate.VALID);
		this.productUnitService.removeProductUnit(this.getUserContext(), productUnit);
		assertEquals(EntityStatus.INACTIVE, productUnit.getEntityStatus());
	}

}
