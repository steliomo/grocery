/**
 *
 */
package mz.co.grocery.integ.resources.grocery;

import java.util.Collections;
import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryDTO {

	private final List<Grocery> groceries;

	private final Long totalItems;

	public GroceryDTO(final List<Grocery> groceries, final Long totalItems) {
		this.groceries = groceries;
		this.totalItems = totalItems;
	}

	public List<Grocery> getGroceries() {
		return Collections.unmodifiableList(this.groceries);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
