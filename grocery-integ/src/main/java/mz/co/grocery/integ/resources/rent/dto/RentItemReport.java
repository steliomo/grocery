/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.Period;

import mz.co.grocery.core.rent.model.RentItem;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemReport {

	private final String itemName;

	private final BigDecimal price;

	private final BigDecimal quantity;

	private final Integer days;

	private final BigDecimal discount;

	private final BigDecimal value;

	public RentItemReport(final RentItem rentItem) {
		rentItem.setTotal();

		this.itemName = rentItem.getItem().getName();
		this.price = rentItem.getItem().getSalePrice();
		this.quantity = rentItem.getQuantity();
		this.days = Period.between(rentItem.getStartDate(), rentItem.getEndDate()).getDays();
		this.discount = rentItem.getDiscount();
		this.value = rentItem.getTotal();
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

	public Integer getDays() {
		return this.days;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public BigDecimal getValue() {
		return this.value;
	}

}
