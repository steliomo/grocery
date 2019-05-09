/**
 *
 */
package mz.co.grocery.core.product.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.ProductDescriptionTemplate;
import mz.co.grocery.core.product.dao.ProductDescriptionDAO;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductDescriptionServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ProductDescriptionDAO productDescriptionDAO;

	@InjectMocks
	private final ProductDescriptionService productDescriptionService = new ProductDescriptionServiceImpl();

	@Test
	public void shouldRemoveProductDescription() throws BusinessException {
		final ProductDescription productDescription = EntityFactory.gimme(ProductDescription.class,
		        ProductDescriptionTemplate.VALID);

		this.productDescriptionService.removeProductDescription(this.getUserContext(), productDescription);

		assertEquals(EntityStatus.INACTIVE, productDescription.getEntityStatus());
	}

}
