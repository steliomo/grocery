/**
 *
 */
package mz.co.grocery.core.domain.pos;

/**
 * @author Stélio Moiane
 *
 */
public class BillItem {

	private String itemName;

	private String unitPrice;

	private String quantity;

	private String value;

	public BillItem(final String itemName, final String unitPrice, final String quantity, final String value) {
		this.itemName = itemName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.value = value;
	}

	public String getItemName() {
		return this.itemName;
	}

	public String getUnitPrice() {
		return this.unitPrice;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public String getValue() {
		return this.value;
	}
}
