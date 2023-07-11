/**
 *
 */
package mz.co.grocery.persistence.rent.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

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

import org.hibernate.LazyInitializationException;

import mz.co.grocery.core.domain.rent.LoadStatus;
import mz.co.grocery.core.domain.rent.ReturnStatus;
import mz.co.grocery.persistence.rent.repository.RentItemRepository;
import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries(@NamedQuery(name = RentItemRepository.QUERY_NAME.fetchByUuid, query = RentItemRepository.QUERY.fetchByUuid))
@Entity
@Table(name = "RENT_ITEMS")
public class RentItemEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ID", nullable = false)
	private RentEntity rent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private StockEntity stock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ITEM_ID")
	private ServiceItemEntity serviceItem;

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

	public Optional<RentEntity> getRent() {
		try {
			Optional.ofNullable(this.rent).ifPresent(rent -> rent.getTotalPaid());
			return Optional.ofNullable(this.rent);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setRent(final RentEntity rent) {
		this.rent = rent;
	}

	public Optional<StockEntity> getStock() {
		try {
			Optional.ofNullable(this.stock).ifPresent(stock -> stock.getQuantity());
			return Optional.ofNullable(this.stock);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setStock(final StockEntity stock) {
		this.stock = stock;
	}

	public Optional<ServiceItemEntity> getServiceItem() {
		try {
			Optional.ofNullable(this.serviceItem).ifPresent(serviceItem -> serviceItem.getSalePrice());
			return Optional.ofNullable(this.serviceItem);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setServiceItem(final ServiceItemEntity serviceItem) {
		this.serviceItem = serviceItem;
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

	public void setLoadedQuantity(final BigDecimal loadedQuantity) {
		this.loadedQuantity = loadedQuantity;
	}

	public LocalDate getLoadingDate() {
		return this.loadingDate;
	}

	public void setLoadingDate(final LocalDate loadingDate) {
		this.loadingDate = loadingDate;
	}

	public BigDecimal getReturnedQuantity() {
		return this.returnedQuantity;
	}

	public void setReturnedQuantity(final BigDecimal returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
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

	public void setPlannedTotal(final BigDecimal plannedTotal) {
		this.plannedTotal = plannedTotal;
	}

	public Boolean getStockable() {
		return this.stockable;
	}

	public void setStockable(final Boolean stockable) {
		this.stockable = stockable;
	}

	public LoadStatus getLoadStatus() {
		return this.loadStatus;
	}

	public void setLoadStatus(final LoadStatus loadStatus) {
		this.loadStatus = loadStatus;
	}

	public ReturnStatus getReturnStatus() {
		return this.returnStatus;
	}

	public void setReturnStatus(final ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}
}
