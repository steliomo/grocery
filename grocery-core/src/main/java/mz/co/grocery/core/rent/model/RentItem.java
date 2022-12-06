/**
 *
 */
package mz.co.grocery.core.rent.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.item.model.Item;
import mz.co.grocery.core.item.model.ItemType;
import mz.co.grocery.core.rent.dao.RentItemDAO;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries(@NamedQuery(name = RentItemDAO.QUERY_NAME.fetchByUuid, query = RentItemDAO.QUERY.fetchByUuid))
@Entity
@Table(name = "RENT_ITEMS")
public class RentItem extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ID", nullable = false)
	private Rent rent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private Stock stock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ITEM_ID")
	private ServiceItem serviceItem;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	@NotNull
	@Column(name = "START_DATE", nullable = false)
	private LocalDate startDate;

	@NotNull
	@Column(name = "END_DATE", nullable = false)
	private LocalDate endDate;

	@Column(name = "DISCOUNT")
	private BigDecimal discount;

	@Column(name = "TOTAL")
	private BigDecimal total;

	@NotNull
	@Column(name = "RETURNABLE", nullable = false)
	private Boolean returnable;

	@Enumerated(EnumType.STRING)
	@Column(name = "RETURN_STATUS", nullable = false, length = 15)
	private ReturnStatus returnStatus;

	@Column(name = "DESCRIPTION", length = 150)
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rentItem")
	private final Set<ReturnItem> returnItems = new HashSet<>();

	public Rent getRent() {
		return this.rent;
	}

	public void setRent(final Rent rent) {
		this.rent = rent;
	}

	public Item getItem() {
		return this.stock != null ? this.stock : this.serviceItem;
	}

	public void setItem(final Item item) {
		if (ItemType.PRODUCT.equals(item.getType())) {
			this.stock = (Stock) item;
			return;
		}

		this.serviceItem = (ServiceItem) item;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final LocalDate endDate) {
		this.endDate = endDate;
	}

	public ItemType getType() {
		return this.getItem().getType();
	}

	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setTotal() {
		final long days = Duration.between(this.startDate.atStartOfDay(), this.endDate.atStartOfDay()).toDays();
		this.total = this.getItem().getRentPrice().multiply(this.quantity).multiply(new BigDecimal(days)).subtract(this.discount);
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public ReturnStatus getReturnStatus() {
		return this.returnStatus;
	}

	public RentItem setReturnable() {
		this.returnable = this.getItem().isReturnable();
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Boolean isReturnable() {
		return this.returnable;
	}

	public BigDecimal returned() {
		return this.returnItems.stream().map(ReturnItem::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal toReturn() {
		return this.quantity.subtract(this.returned());
	}

	public void setReturnStatus(final BigDecimal quantity) {

		if (!this.isReturnable() && quantity.compareTo(BigDecimal.ZERO) == BigDecimal.ZERO.intValue()) {
			this.returnStatus = ReturnStatus.NA;
			return;
		}

		if (this.quantity.compareTo(this.returned()) == BigDecimal.ZERO.intValue()) {
			this.returnStatus = ReturnStatus.COMPLETE;
			return;
		}

		this.returnStatus = ReturnStatus.PENDING;
	}

	public void addReturnItem(final ReturnItem returnItem) {
		this.returnItems.add(returnItem);
	}

	public Set<ReturnItem> getReturnItems() {
		return this.returnItems;
	}
}
