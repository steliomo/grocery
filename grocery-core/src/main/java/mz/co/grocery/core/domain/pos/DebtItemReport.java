/**
 *
 */
package mz.co.grocery.core.domain.pos;

/**
 * @author Stélio Moiane
 *
 */
public class DebtItemReport {

	private String debtDate;

	private String name;

	private String quantity;

	private String price;

	private String debtItemValue;

	public DebtItemReport(final String debtDate, final String name, final String quantity, final String price, final String debtItemValue) {
		this.debtDate = debtDate;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.debtItemValue = debtItemValue;
	}

	public String getDebtDate() {
		return this.debtDate;
	}

	public String getName() {
		return this.name;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public String getPrice() {
		return this.price;
	}

	public String getDebtItemValue() {
		return this.debtItemValue;
	}
}
