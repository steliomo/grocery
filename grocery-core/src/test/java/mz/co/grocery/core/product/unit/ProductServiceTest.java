/**
 *
 */
package mz.co.grocery.core.product.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.fixturefactory.ProductTemplate;
import mz.co.grocery.core.product.dao.ProductDAO;
import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductServiceImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public class ProductServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ProductDAO ProductDAO;

	@InjectMocks
	private final ProductService productService = new ProductServiceImpl();

	@Test
	public void shouldRemoveProduct() throws BusinessException {
		final Product product = EntityFactory.gimme(Product.class, ProductTemplate.VALID);
		this.productService.removeProduct(this.getUserContext(), product);

		assertEquals(EntityStatus.INACTIVE, product.getEntityStatus());
	}
}
