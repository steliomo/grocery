/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
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

import org.hibernate.LazyInitializationException;

import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.guide.entity.GuideEntity;
import mz.co.grocery.persistence.sale.repository.SaleRepository;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = SaleRepository.QUERY_NAME.findPerPeriod, query = SaleRepository.QUERY.findPerPeriod),
	@NamedQuery(name = SaleRepository.QUERY_NAME.findMonthlyPerPeriod, query = SaleRepository.QUERY.findMonthlyPerPeriod),
	@NamedQuery(name = SaleRepository.QUERY_NAME.findPendingOrImpletePaymentSaleStatusByCustomer, query = SaleRepository.QUERY.findPendingOrImpletePaymentSaleStatusByCustomer),
	@NamedQuery(name = SaleRepository.QUERY_NAME.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer, query = SaleRepository.QUERY.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer),
	@NamedQuery(name = SaleRepository.QUERY_NAME.fetchByUuid, query = SaleRepository.QUERY.fetchByUuid),
	@NamedQuery(name = SaleRepository.QUERY_NAME.fetchSalesWithDeliveryGuidesByCustomer, query = SaleRepository.QUERY.fetchSalesWithDeliveryGuidesByCustomer)
})
@Entity
@Table(name = "SALES")
public class SaleEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private UnitEntity unit;

	@NotNull
	@Column(name = "SALE_DATE", nullable = false)
	private LocalDate saleDate;

	@NotNull
	@Column(name = "BILLING", nullable = false)
	private BigDecimal billing = BigDecimal.ZERO;

	@NotNull
	@Column(name = "TOTAL", nullable = false)
	private BigDecimal total = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")
	private Set<SaleItemEntity> items = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private CustomerEntity customer;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "SALE_TYPE", nullable = false, length = 20)
	private SaleType saleType;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "SALE_STATUS", nullable = false, length = 20)
	private SaleStatus saleStatus;

	@Column(name = "TOTAL_PAID")
	private BigDecimal totalPaid;

	@Column(name = "DUE_DATE")
	private LocalDate dueDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "DELIVERY_STATUS", length = 20, nullable = false)
	private DeliveryStatus deliveryStatus;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sale")
	private Set<GuideEntity> guides;

	public Optional<UnitEntity> getUnit() {
		try {
			Optional.ofNullable(this.unit).ifPresent(unit -> unit.getName());
			return Optional.ofNullable(this.unit);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(final LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public void setBilling(final BigDecimal billing) {
		this.billing = billing;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	public Optional<Set<SaleItemEntity>> getItems() {
		try {
			Optional.ofNullable(this.items).ifPresent(items -> items.size());
			return Optional.ofNullable(this.items);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setItems(final Set<SaleItemEntity> items) {
		this.items = items;
	}

	public Optional<CustomerEntity> getCustomer() {
		try {
			Optional.ofNullable(this.customer).ifPresent(customer -> customer.getName());
			return Optional.ofNullable(this.customer);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setCustomer(final CustomerEntity customer) {
		this.customer = customer;
	}

	public SaleType getSaleType() {
		return this.saleType;
	}

	public void setSaleType(final SaleType saleType) {
		this.saleType = saleType;
	}

	public SaleStatus getSaleStatus() {
		return this.saleStatus;
	}

	public void setSaleStatus(final SaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public LocalDate getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public DeliveryStatus getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(final DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Optional<Set<GuideEntity>> getGuides() {
		try {
			Optional.ofNullable(this.guides).ifPresent(guides -> guides.size());
			return Optional.ofNullable(this.guides);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}
}
