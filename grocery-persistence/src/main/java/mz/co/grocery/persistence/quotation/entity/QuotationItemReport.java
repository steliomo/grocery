/**
 *
 */
package mz.co.grocery.persistence.quotation.entity;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationItemReport {

	private String item;

	private String itemName;

	private String price;

	private String quantity;

	private String days;

	private String value;

	public QuotationItemReport(final String item, final String itemName, final String price, final String quantity, final String days,
			final String value) {
		this.item = item;
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
		this.days = days;
		this.value = value;
	}

	public String getItem() {
		return this.item;
	}

	public String getItemName() {
		return this.itemName;
	}

	public String getPrice() {
		return this.price;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public String getDays() {
		return this.days;
	}

	public String getValue() {
		return this.value;
	}
}
