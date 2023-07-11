
/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionDTO extends GenericDTO {

	private ProductDTO productDTO;

	private String description;

	private ProductUnitDTO productUnitDTO;

	private String name;

	public Optional<ProductDTO> getProductDTO() {
		return Optional.ofNullable(this.productDTO);
	}

	public void setProductDTO(final ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Optional<ProductUnitDTO> getProductUnitDTO() {
		return Optional.ofNullable(this.productUnitDTO);
	}

	public void setProductUnitDTO(final ProductUnitDTO productUnitDTO) {
		this.productUnitDTO = productUnitDTO;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
