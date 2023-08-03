/**
 *
 */
package mz.co.grocery.core.domain.quotation;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.item.Item;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationItem extends Domain {

	private Quotation quotation;

	private Item item;

	private BigDecimal quantity;

	private BigDecimal days;

	public void setQuotation(final Quotation quotation) {
		this.quotation = quotation;
	}

	public Optional<Quotation> getQuotation() {
		return Optional.ofNullable(this.quotation);
	}

	public void setItem(final Item item) {
		this.item = item;
	}

	public Optional<Item> getItem() {
		return Optional.ofNullable(this.item);
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setDays(final BigDecimal days) {
		this.days = days;
	}

	public BigDecimal getDays() {
		if (this.quotation == null) {
			return this.days;
		}

		if (QuotationType.SALE.equals(this.quotation.getType())) {
			return BigDecimal.ZERO;
		}

		return this.days;
	}

	public BigDecimal getTotal() {

		if (QuotationType.RENT.equals(this.quotation.getType())) {
			return this.item.getRentPrice().multiply(this.quantity).multiply(this.days);
		}

		return this.item.getSalePrice().multiply(this.quantity);
	}

	public String getName() {
		return this.item.getName();
	}

	public BigDecimal getPrice() {
		if (QuotationType.RENT.equals(this.quotation.getType())) {
			return this.item.getRentPrice();
		}
		return this.item.getSalePrice();
	}
}
