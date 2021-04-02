
/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import mz.co.grocery.core.item.model.Product;
import mz.co.grocery.core.item.model.ProductDescription;
import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionDTO extends GenericDTO<ProductDescription> {

	private ProductDTO productDTO;

	private String description;

	private ProductUnitDTO productUnitDTO;

	public ProductDescriptionDTO() {
	}

	public ProductDescriptionDTO(final ProductDescription productDescription) {
		super(productDescription);
		this.mapper(productDescription);
	}

	@Override
	public void mapper(final ProductDescription productDescription) {

		final Product product = productDescription.getProduct();

		if (ProxyUtil.isInitialized(product)) {
			this.productDTO = new ProductDTO(product);
		}

		this.description = productDescription.getDescription();

		final ProductUnit productUnit = productDescription.getProductUnit();

		if (ProxyUtil.isInitialized(productUnit)) {
			this.productUnitDTO = new ProductUnitDTO(productUnit);
		}
	}

	@Override
	public ProductDescription get() {
		final ProductDescription productDescription = this.get(new ProductDescription());

		productDescription.setProduct(this.productDTO.get());
		productDescription.setDescription(this.description);

		if (this.productUnitDTO != null) {
			productDescription.setProductUnit(this.productUnitDTO.get());
		}

		return productDescription;
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

	public String getName() {

		if (this.productUnitDTO == null) {
			return "";
		}

		return new StringBuilder()
				.append(this.productDTO.getName())
				.append(" ")
				.append(this.description).append(" ")
				.append(this.productUnitDTO.getUnit()).append(" ")
				.append(this.productUnitDTO.getProductUnitType())
				.toString();
	}
}
