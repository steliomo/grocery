/**
 *
 */
package mz.co.grocery.integ.resources.grocery.dto;

import java.util.Collections;
import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class GroceriesDTO {

	private final List<GroceryDTO> groceriesDTO;

	private final Long totalItems;

	public GroceriesDTO(final List<GroceryDTO> groceriesDTO, final Long totalItems) {
		this.groceriesDTO = groceriesDTO;
		this.totalItems = totalItems;
	}

	public List<GroceryDTO> getGroceriesDTO() {
		return Collections.unmodifiableList(this.groceriesDTO);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
