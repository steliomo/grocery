/**
 *
 */
package mz.co.grocery.core.domain.pos;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Stélio Moiane
 *
 */

public class DebtItem {

	private LocalDate debtDate;

	private String name;

	private BigDecimal quantity;

	private BigDecimal price;

	private BigDecimal debtItemValue;

	public DebtItem(final LocalDate debtDate, final String name, final BigDecimal quantity, final BigDecimal price, final BigDecimal debtItemValue) {
		this.debtDate = debtDate;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.debtItemValue = debtItemValue;
	}

	public LocalDate getDebtDate() {
		return this.debtDate;
	}

	public void setDeptDate(final LocalDate debtDate) {
		this.debtDate = debtDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDebtItemValue() {
		return this.debtItemValue;
	}

	public void setDebtItemValue(final BigDecimal debtItemValue) {
		this.debtItemValue = debtItemValue;
	}
}
