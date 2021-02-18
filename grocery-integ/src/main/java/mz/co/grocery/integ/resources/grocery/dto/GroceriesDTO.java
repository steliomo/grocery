/**
 *
 */
package mz.co.grocery.integ.resources.grocery.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.grocery.model.Grocery;

/**
 * @author Stélio Moiane
 *
 */
public class GroceriesDTO {

	private final List<Grocery> groceries;

	private final Long totalItems;

	public GroceriesDTO(final List<Grocery> groceries, final Long totalItems) {
		this.groceries = groceries;
		this.totalItems = totalItems;
	}

	public List<GroceryDTO> getGroceriesDTO() {
		return Collections.unmodifiableList(this.groceries.stream().map(unit -> new GroceryDTO(unit)).collect(Collectors.toList()));
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
