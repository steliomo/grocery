/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import static mz.co.grocery.integ.resources.util.ProxyUtil.isInitialized;

import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionDTO extends GenericDTO<ProductDescription> {

	private ProductDTO productDTO;

	private String description;

	private ProductUnitDTO productUnitDTO;

	public ProductDescriptionDTO(final ProductDescription productDescription) {
		super(productDescription);
		this.mapper(productDescription);
	}

	@Override
	public void mapper(final ProductDescription productDescription) {

		final Product product = productDescription.getProduct();

		if (isInitialized(product)) {
			this.productDTO = new ProductDTO(product);
		}

		this.description = productDescription.getDescription();

		final ProductUnit productUnit = productDescription.getProductUnit();

		if (isInitialized(productUnit)) {
			this.productUnitDTO = new ProductUnitDTO(productUnit);
		}
	}

	public ProductDTO getProductDTO() {
		return this.productDTO;
	}

	public String getDescription() {
		return this.description;
	}

	public ProductUnitDTO getProductUnitDTO() {
		return this.productUnitDTO;
	}
}
