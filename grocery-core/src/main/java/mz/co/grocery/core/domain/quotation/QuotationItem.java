/**
 *
 */
package mz.co.grocery.core.domain.quotation;

import java.math.BigDecimal;

import mz.co.grocery.core.saleable.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationItem {

	private Quotation quotation;

	private Stock stock;

	private BigDecimal quantity;

	private BigDecimal days;

	public void setQuotation(final Quotation quotation) {
		this.quotation = quotation;
	}

	public Quotation getQuotation() {
		return this.quotation;
	}

	public Stock getStock() {
		return this.stock;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public BigDecimal getDays() {
		return this.days;
	}
}
