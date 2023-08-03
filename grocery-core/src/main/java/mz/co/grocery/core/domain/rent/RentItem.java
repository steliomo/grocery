/**
 *
 */
package mz.co.grocery.core.domain.rent;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.item.Item;
import mz.co.grocery.core.domain.item.ItemType;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class RentItem extends Domain {

	private Rent rent;

	private Stock stock;

	private ServiceItem serviceItem;

	private BigDecimal plannedQuantity;

	private BigDecimal plannedDays;

	private BigDecimal loadedQuantity;

	private LocalDate loadingDate;

	private BigDecimal returnedQuantity;

	private LocalDate returnDate;

	private BigDecimal discount;

	private BigDecimal plannedTotal;

	private Boolean stockable;

	private LoadStatus loadStatus;

	private ReturnStatus returnStatus;

	public RentItem() {
		this.plannedTotal = BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
	}

	public Optional<Rent> getRent() {
		return Optional.ofNullable(this.rent);
	}

	public void setRent(final Rent rent) {
		this.rent = rent;
	}

	public Optional<Item> getItem() {
		return this.stock != null ? Optional.ofNullable(this.stock) : Optional.ofNullable(this.serviceItem);
	}

	public void setItem(final Item item) {
		if (ItemType.PRODUCT.equals(item.getType())) {
			this.stock = (Stock) item;
			return;
		}

		this.serviceItem = (ServiceItem) item;
	}

	public ItemType getType() {
		return this.getItem().get().getType();
	}

	public RentItem setStockable() {
		this.stockable = this.getItem().get().isStockable();
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
		this.plannedTotal = this.getItem().get().getRentPrice().multiply(this.plannedQuantity).multiply(this.plannedDays).subtract(this.discount);
	}

	public BigDecimal getRentalChunkValueOnLoading(final LocalDate calculatedDate) {

		if (this.loadingDate == null) {
			return BigDecimal.ZERO;
		}

		final long days = Duration.between(this.loadingDate.atStartOfDay(), calculatedDate.atStartOfDay()).toDays();

		return this.getItem().get().getRentPrice().multiply(this.loadedQuantity).multiply(new BigDecimal(days));
	}

	public BigDecimal getRentalChunkValueOnReturning(final LocalDate calculatedDate, final BigDecimal quantity) {

		if (this.loadingDate == null) {
			return BigDecimal.ZERO;
		}

		final long days = Duration.between(this.loadingDate.atStartOfDay(), calculatedDate.atStartOfDay()).toDays();

		return this.getItem().get().getRentPrice().multiply(quantity).multiply(new BigDecimal(days));
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

		this.plannedTotal = this.getItem().get().getRentPrice().multiply(this.plannedDays).multiply(this.plannedQuantity.subtract(returnedQuantity))
				.subtract(this.discount);
	}

	public void setLoadedQuantity(final BigDecimal loadedQuantity) {
		this.loadedQuantity = loadedQuantity;
	}

	public void setReturnedQuantity(final BigDecimal returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}

	public void setPlannedTotal(final BigDecimal plannedTotal) {
		this.plannedTotal = plannedTotal;
	}

	public void setStockable(final Boolean stockable) {
		this.stockable = stockable;
	}

	public void setLoadStatus(final LoadStatus loadStatus) {
		this.loadStatus = loadStatus;
	}

	public void setReturnStatus(final ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
}
