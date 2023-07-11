/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDTO extends GenericDTO {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
