/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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

import org.hibernate.LazyInitializationException;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.customer.model.SaleType;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.util.BigDecimalUtil;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = SaleDAO.QUERY_NAME.findPerPeriod, query = SaleDAO.QUERY.findPerPeriod),
	@NamedQuery(name = SaleDAO.QUERY_NAME.findMonthlyPerPeriod, query = SaleDAO.QUERY.findMonthlyPerPeriod),
	@NamedQuery(name = SaleDAO.QUERY_NAME.findPendingOrImpletePaymentSaleStatusByCustomer, query = SaleDAO.QUERY.findPendingOrImpletePaymentSaleStatusByCustomer),
	@NamedQuery(name = SaleDAO.QUERY_NAME.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer, query = SaleDAO.QUERY.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer),
	@NamedQuery(name = SaleDAO.QUERY_NAME.fetchByUuid, query = SaleDAO.QUERY.fetchByUuid),
	@NamedQuery(name = SaleDAO.QUERY_NAME.fetchSalesWithDeliveryGuidesByCustomer, query = SaleDAO.QUERY.fetchSalesWithDeliveryGuidesByCustomer)
})
@Entity
@Table(name = "SALES")
public class Sale extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private Grocery grocery;

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
	private Set<SaleItem> items = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

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
	private Set<Guide> guides;

	public Grocery getGrocery() {
		return this.grocery;
	}

	public void setGrocery(final Grocery grocery) {
		this.grocery = grocery;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public void setSaleDate(final LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public Set<SaleItem> getItems() {
		return Collections.unmodifiableSet(this.items);
	}

	public void addItem(final SaleItem item) {

		if (this.items == null) {
			this.items = new HashSet<>();
		}

		this.items.add(item);
	}

	public void calculateTotal() {
		this.total = this.items.stream().map(SaleItem::getTotalSaleItem).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void calculateBilling() {
		this.billing = this.items.stream().map(SaleItem::getTotalBilling).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return this.customer;
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

	public BigDecimal getRemainingPayment() {
		return this.total.subtract(this.totalPaid);
	}

	public void updateTotalPaid(final BigDecimal paymentValue) {
		this.totalPaid = this.totalPaid.add(paymentValue);
	}

	public void updateSaleStatus() {

		if (BigDecimalUtil.isEqual(this.total, this.totalPaid)) {
			this.saleStatus = SaleStatus.COMPLETE;
			return;
		}

		this.saleStatus = SaleStatus.INCOMPLETE;
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

	public void updateDeliveryStatus() throws BusinessException {
		if (this.items == null) {
			throw new BusinessException("Sale items cannot be null");
		}

		final boolean allMatch = this.items.stream().allMatch(saleItem -> DeliveryStatus.COMPLETE.equals(saleItem.getDeliveryStatus()));

		if (allMatch) {
			this.deliveryStatus = DeliveryStatus.COMPLETE;
			return;
		}

		this.deliveryStatus = DeliveryStatus.INCOMPLETE;
	}

	public Set<Guide> getGuides() {

		if (this.guides == null) {
			return new HashSet<>();
		}

		try {
			this.guides.size();
		} catch (final LazyInitializationException e) {
			return new HashSet<>();
		}
		return Collections.unmodifiableSet(this.guides);
	}
}
