/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class DebtItemDTO {

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate debtDate;

	private String name;

	private BigDecimal quantity;

	private BigDecimal price;

	private BigDecimal debtItemValue;

	public LocalDate getDebtDate() {
		return this.debtDate;
	}

	public void setDebtDate(final LocalDate debtDate) {
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
