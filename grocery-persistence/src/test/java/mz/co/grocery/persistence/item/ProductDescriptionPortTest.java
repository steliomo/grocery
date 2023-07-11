/**
 *
 */
package mz.co.grocery.persistence.item;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.item.out.ProductDescriptionPort;
import mz.co.grocery.core.application.item.out.ProductPort;
import mz.co.grocery.core.application.item.out.ProductUnitPort;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ProductDescriptionTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionPortTest extends AbstractIntegServiceTest {

	@Inject
	private ProductDescriptionPort productDescriptionPort;

	@Inject
	private ProductPort productPort;

	@Inject
	private ProductUnitPort productUnitPort;

	private String productDescriptionUuid;

	@Before
	public void before() {
		final List<ProductDescription> productDescriptions = EntityFactory.gimme(ProductDescription.class, 10,
				ProductDescriptionTemplate.VALID);
		productDescriptions.forEach(productDescription -> {
			this.createProductDescription(productDescription);
		});
	}

	private void createProductDescription(ProductDescription productDescription) {
		try {
			final Product product = this.productPort.createProduct(this.getUserContext(), productDescription.getProduct().get());
			final ProductUnit productUnit = this.productUnitPort.createProductUnit(this.getUserContext(), productDescription.getProductUnit().get());
			productDescription.setProduct(product);
			productDescription.setProductUnit(productUnit);
			productDescription = this.productDescriptionPort.createProductDescription(this.getUserContext(), productDescription);
			this.productDescriptionUuid = productDescription.getUuid();
		} catch (final BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldFetchAllProductDesctiptions() throws BusinessException {

		final int currentPage = 0;
		final int maxResult = 10;

		final List<ProductDescription> productDescriptions = this.productDescriptionPort
				.fetchdAllProductDescriptions(currentPage, maxResult);
		Assert.assertFalse(productDescriptions.isEmpty());
	}

	@Test
	public void shouldCountProductDescriptions() throws BusinessException {
		final Long totalItems = this.productDescriptionPort.countProductDescriptions();
		Assert.assertTrue(10L <= totalItems);
	}

	@Test
	public void shouldFetchProductDescriptionByDescription() throws BusinessException {
		final String description = "milho";
		final List<ProductDescription> productDescriptions = this.productDescriptionPort
				.fetchProductDescriptionByDescription(description);

		Assert.assertFalse(productDescriptions.isEmpty());
		productDescriptions.forEach(productDescription -> {
			Assert.assertTrue(productDescription.getDescription().contains(description));
		});
	}

	@Test
	public void shouldFetchProductDescriptionByUuid() throws BusinessException {
		final ProductDescription productDescription = this.productDescriptionPort
				.fetchProductDescriptionByUuid(this.productDescriptionUuid);

		Assert.assertNotNull(productDescription);
		Assert.assertEquals(this.productDescriptionUuid, productDescription.getUuid());
	}
}
