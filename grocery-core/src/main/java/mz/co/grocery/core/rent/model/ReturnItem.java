/**
 *
 */
package mz.co.grocery.core.rent.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@Entity
@Table(name = "RETURN_ITEMS")
public class ReturnItem extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ITEM_ID", nullable = false)
	private RentItem rentItem;

	@NotNull
	@Column(name = "RETURN_DATE", nullable = false)
	private LocalDate returnDate;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	public RentItem getRentItem() {
		return this.rentItem;
	}

	public void setRentItem(final RentItem rentItem) {
		this.rentItem = rentItem;
	}

	public LocalDate getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(final LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}
}
