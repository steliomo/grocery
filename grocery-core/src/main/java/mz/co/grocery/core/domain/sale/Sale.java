/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class Sale extends Domain {

	private Unit unit;

	private Customer customer;

	private LocalDate saleDate;

	private BigDecimal billing;

	private BigDecimal total;

	private SaleType saleType;

	private SaleStatus saleStatus;

	private BigDecimal totalPaid;

	private LocalDate dueDate;

	private DeliveryStatus deliveryStatus;

	private Set<SaleItem> items;

	private Set<Guide> guides;

	public Sale() {
		this.items = new HashSet<>();
		this.guides = new HashSet<>();
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
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

	public Optional<Set<SaleItem>> getItems() {
		return Optional.ofNullable(this.items);
	}

	public void addItem(final SaleItem item) {
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

	public Optional<Customer> getCustomer() {
		return Optional.ofNullable(this.customer);
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
			this.saleStatus = SaleStatus.CLOSED;
			return;
		}

		this.saleStatus = SaleStatus.IN_PROGRESS;
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

	public Optional<Set<Guide>> getGuides() {
		return Optional.ofNullable(Collections.unmodifiableSet(this.guides));
	}

	public void setBilling(final BigDecimal billing) {
		this.billing = billing;
	}

	public void setTotal(final BigDecimal total) {
		this.total = total;
	}

	public void addGuide(final Guide guide) {
		this.guides.add(guide);
	}
}
