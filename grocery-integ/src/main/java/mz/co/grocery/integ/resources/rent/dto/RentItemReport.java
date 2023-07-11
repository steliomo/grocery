/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.domain.rent.RentItem;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemReport {

	private final String itemName;

	private final BigDecimal price;

	private final BigDecimal quantity;

	private final BigDecimal days;

	private final BigDecimal discount;

	private final BigDecimal value;

	public RentItemReport(final RentItem rentItem) {

		this.itemName = rentItem.getItem().get().getName();
		this.price = rentItem.getItem().get().getRentPrice();
		this.quantity = rentItem.getPlannedQuantity();
		this.discount = rentItem.getDiscount();
		this.days = rentItem.getPlannedDays();
		rentItem.calculatePlannedTotal();
		this.value = rentItem.getPlannedTotal();
	}

	public String getItemName() {
		return this.itemName;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public BigDecimal getDays() {
		return this.days;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public BigDecimal getValue() {
		return this.value;
	}

}
