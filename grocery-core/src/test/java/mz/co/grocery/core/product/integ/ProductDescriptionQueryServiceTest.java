/**
 *
 */
package mz.co.grocery.core.product.integ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.ProductDescriptionTemplate;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.product.service.ProductDescriptionQueryService;
import mz.co.grocery.core.product.service.ProductDescriptionService;
import mz.co.grocery.core.product.service.ProductService;
import mz.co.grocery.core.product.service.ProductUnitService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ProductDescriptionService productDescriptionService;

	@Inject
	private ProductDescriptionQueryService productDescriptionQueryService;

	@Inject
	private ProductService productService;

	@Inject
	private ProductUnitService productUnitService;

	private String productDescriptionUuid;

	@Before
	public void before() {
		final List<ProductDescription> productDescriptions = EntityFactory.gimme(ProductDescription.class, 10,
		        ProductDescriptionTemplate.VALID);
		productDescriptions.forEach(productDescription -> {
			this.createProductDescription(productDescription);
		});
	}

	private void createProductDescription(final ProductDescription productDescription) {
		try {
			this.productService.createProduct(this.getUserContext(), productDescription.getProduct());
			this.productUnitService.createProductUnit(this.getUserContext(), productDescription.getProductUnit());
			this.productDescriptionService.createProductDescription(this.getUserContext(), productDescription);
			this.productDescriptionUuid = productDescription.getUuid();
		}
		catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFetchAllProductDesctiptions() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<ProductDescription> productDescriptions = this.productDescriptionQueryService
		        .fetchdAllProductDescriptions(currentPage, maxResult);
		assertFalse(productDescriptions.isEmpty());
	}

	@Test
	public void shouldCountProductDescriptions() throws BusinessException {
		final Long totalItems = this.productDescriptionQueryService.countProductDescriptions();
		assertTrue(10L <= totalItems);
	}

	@Test
	public void shouldFetchProductDescriptionByDescription() throws BusinessException {
		final String description = "milho";
		final List<ProductDescription> productDescriptions = this.productDescriptionQueryService
		        .fetchProductDescriptionByDescription(description);

		assertFalse(productDescriptions.isEmpty());
		productDescriptions.forEach(productDescription -> {
			assertTrue(productDescription.getDescription().contains(description));
		});
	}

	@Test
	public void shouldFetchProductDescriptionByUuid() throws BusinessException {
		final ProductDescription productDescription = this.productDescriptionQueryService
		        .fetchProductDescriptionByUuid(this.productDescriptionUuid);

		assertNotNull(productDescription);
		assertEquals(this.productDescriptionUuid, productDescription.getUuid());
	}
}
