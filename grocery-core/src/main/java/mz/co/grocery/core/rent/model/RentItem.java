/**
 *
 */
package mz.co.grocery.core.rent.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
	@Column(name = "PLANNED_QUANTITY", nullable = false)
	private BigDecimal plannedQuantity;

	@NotNull
	@Column(name = "PLANNED_DAYS", nullable = false)
	private BigDecimal plannedDays;

	@Column(name = "LOADED_QUANTITY")
	private BigDecimal loadedQuantity;

	@Column(name = "LOADING_DATE")
	private LocalDate loadingDate;

	@Column(name = "RETURNED_QUANTITY")
	private BigDecimal returnedQuantity;

	@Column(name = "RETURN_DATE")
	private LocalDate returnDate;

	@Column(name = "DISCOUNT")
	private BigDecimal discount;

	@NotNull
	@Column(name = "PLANNED_TOTAL", nullable = false)
	private BigDecimal plannedTotal;

	@NotNull
	@Column(name = "STOCKABLE", nullable = false)
	private Boolean stockable;

	@Enumerated(EnumType.STRING)
	@Column(name = "LOAD_STATUS", length = 15)
	private LoadStatus loadStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "RETURN_STATUS", length = 15)
	private ReturnStatus returnStatus;

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

	public ItemType getType() {
		return this.getItem().getType();
	}

	public RentItem setStockable() {
		this.stockable = this.getItem().isStockable();
		return this;
	}

	public Boolean isStockable() {
		return this.stockable;
	}

	public BigDecimal getPlannedQuantity() {
		return this.plannedQuantity;
	}

	public void setPlannedQuantity(final BigDecimal plannedQuantity) {
		this.plannedQuantity = plannedQuantity;
	}

	public BigDecimal getPlannedDays() {
		return this.plannedDays;
	}

	public void setPlannedDays(final BigDecimal plannedDays) {
		this.plannedDays = plannedDays;
	}

	public BigDecimal getLoadedQuantity() {
		return this.loadedQuantity;
	}

	public void addLoadedQuantity(final BigDecimal quantity) {
		if (this.loadedQuantity == null) {
			this.loadedQuantity = BigDecimal.ZERO;
		}

		this.loadedQuantity = this.loadedQuantity.add(quantity);
	}

	public BigDecimal getQuantityToLoad() {
		return this.plannedQuantity.subtract(this.loadedQuantity == null ? BigDecimal.ZERO : this.loadedQuantity);
	}

	public BigDecimal getReturnedQuantity() {
		return this.returnedQuantity;
	}

	public void addReturnedQuantity(final BigDecimal quantity) {

		if (this.returnedQuantity == null) {
			this.returnedQuantity = BigDecimal.ZERO;
		}

		this.returnedQuantity = this.returnedQuantity.add(quantity);
	}

	public LocalDate getLoadingDate() {
		return this.loadingDate;
	}

	public void setLoadingDate(final LocalDate loadingDate) {
		this.loadingDate = loadingDate;
	}

	public LocalDate getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(final LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPlannedTotal() {
		return this.plannedTotal;
	}

	public void calculatePlannedTotal() {
		this.plannedTotal = this.getItem().getRentPrice().multiply(this.plannedQuantity).multiply(this.plannedDays).subtract(this.discount);
	}

	public BigDecimal getRentalChunkValueOnLoading(final LocalDate calculatedDate) {

		if (this.loadingDate == null) {
			return BigDecimal.ZERO;
		}

		final long days = Duration.between(this.loadingDate.atStartOfDay(), calculatedDate.atStartOfDay()).toDays();

		return this.getItem().getRentPrice().multiply(this.loadedQuantity).multiply(new BigDecimal(days));
	}

	public BigDecimal getRentalChunkValueOnReturning(final LocalDate calculatedDate, final BigDecimal quantity) {

		if (this.loadingDate == null) {
			return BigDecimal.ZERO;
		}

		final long days = Duration.between(this.loadingDate.atStartOfDay(), calculatedDate.atStartOfDay()).toDays();

		return this.getItem().getRentPrice().multiply(quantity).multiply(new BigDecimal(days));
	}

	public BigDecimal getCurrentRentQuantity() {
		return this.loadedQuantity.subtract(this.returnedQuantity == null ? BigDecimal.ZERO : this.returnedQuantity);
	}

	public LoadStatus getLoadStatus() {
		return this.loadStatus;
	}

	public void setLoadStatus() {

		if (this.loadedQuantity == null) {
			this.loadStatus = LoadStatus.PENDING;
			return;
		}

		if (this.loadedQuantity.compareTo(this.plannedQuantity) == BigDecimal.ZERO.intValue()) {
			this.loadStatus = LoadStatus.COMPLETE;
			return;
		}

		this.loadStatus = LoadStatus.INCOMPLETE;
	}

	public void closeItemLoad() {
		this.loadStatus = LoadStatus.COMPLETE;
	}

	public ReturnStatus getReturnStatus() {
		return this.returnStatus;
	}

	public void setReturnStatus() {

		if (this.returnedQuantity == null) {
			this.returnStatus = ReturnStatus.PENDING;
			return;
		}

		if (this.returnedQuantity.compareTo(this.loadedQuantity) == BigDecimal.ZERO.intValue()) {
			this.returnStatus = ReturnStatus.COMPLETE;
			return;
		}

		this.returnStatus = ReturnStatus.INCOMPLETE;
	}

	public String getName() {
		return this.stock != null ? this.stock.getName() : this.serviceItem.getName();
	}

	public void reCalculateTotalPlanned(final LocalDate returnedDate, final BigDecimal returnedQuantity) {

		final long days = Duration.between(this.loadingDate.atStartOfDay(), returnedDate.atStartOfDay()).toDays();

		if (BigDecimal.ZERO.longValue() != days) {
			return;
		}

		this.plannedTotal = this.getItem().getRentPrice().multiply(this.plannedDays).multiply(this.plannedQuantity.subtract(returnedQuantity))
				.subtract(this.discount);
	}
}
