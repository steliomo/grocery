/**
 *
 */
package mz.co.grocery.integ.resources.product.dto;

import mz.co.grocery.core.product.model.Product;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDTO extends GenericDTO<Product> {

	private String name;

	public ProductDTO(final Product product) {
		super(product);
		this.mapper(product);
	}

	@Override
	public void mapper(final Product product) {
		this.name = product.getName();
	}

	public String getName() {
		return this.name;
	}
}
