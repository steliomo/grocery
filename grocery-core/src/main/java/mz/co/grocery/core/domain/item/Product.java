/**
 *
 */
package mz.co.grocery.core.domain.item;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */

public class Product extends Domain {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
