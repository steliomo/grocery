/**
 *
 */
package mz.co.grocery.core.guide.model;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public class GuideItemReport {

	private final Integer item;

	private final BigDecimal quantity;

	private final String itemName;

	public GuideItemReport(final Integer item, final BigDecimal quantity, final String itemName) {
		this.item = item;
		this.quantity = quantity;
		this.itemName = itemName;
	}

	public Integer getItem() {
		return this.item;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public String getItemName() {
		return this.itemName;
	}
}
