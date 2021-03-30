/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import mz.co.grocery.core.item.model.Product;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDTO extends GenericDTO<Product> {

	private String name;

	public ProductDTO() {
	}

	public ProductDTO(final Product product) {
		super(product);
		this.mapper(product);
	}

	@Override
	public void mapper(final Product product) {
		this.name = product.getName();
	}

	@Override
	public Product get() {
		final Product product = this.get(new Product());
		product.setName(this.name);
		return product;
	}

	public String getName() {
		return this.name;
	}
}
