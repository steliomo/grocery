/**
 *
 */
package mz.co.grocery.core.item;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.ProductDescriptionTemplate;
import mz.co.grocery.core.item.dao.ProductDescriptionDAO;
import mz.co.grocery.core.item.model.ProductDescription;
import mz.co.grocery.core.item.service.ProductDescriptionService;
import mz.co.grocery.core.item.service.ProductDescriptionServiceImpl;
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

		Assert.assertEquals(EntityStatus.INACTIVE, productDescription.getEntityStatus());
	}

}
