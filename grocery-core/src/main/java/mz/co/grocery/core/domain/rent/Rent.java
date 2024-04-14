/**
 *
 */
package mz.co.grocery.core.domain.rent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */

public class Rent extends Domain {

	private Unit unit;

	private LocalDate rentDate;

	private Customer customer;

	private RentStatus rentStatus;

	private PaymentStatus paymentStatus;

	private LoadStatus loadStatus;

	private ReturnStatus returnStatus;

	private BigDecimal totalEstimated;

	private BigDecimal totalCalculated;

	private BigDecimal totalPaid;

	private Set<RentItem> rentItems;

	private Set<RentPayment> rentPayments;

	private Set<Guide> guides;

	public Rent() {
		this.rentStatus = RentStatus.OPENED;
		this.paymentStatus = PaymentStatus.PENDING;
		this.loadStatus = LoadStatus.PENDING;
		this.returnStatus = ReturnStatus.PENDING;
		this.totalEstimated = BigDecimal.ZERO;
		this.totalCalculated = BigDecimal.ZERO;
		this.totalPaid = BigDecimal.ZERO;

		this.rentItems = new HashSet<>();
		this.rentPayments = new HashSet<>();
		this.guides = new HashSet<>();
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public void setRentDate(final LocalDate rentDate) {
		this.rentDate = rentDate;
	}

	public LocalDate getRentDate() {
		return this.rentDate;
	}

	public Optional<Customer> getCustomer() {
		return Optional.ofNullable(this.customer);
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void addRentItem(final RentItem rentItem) {
		this.rentItems.add(rentItem);
	}

	public Optional<Set<RentItem>> getRentItems() {
		return Optional.ofNullable(Collections.unmodifiableSet(this.rentItems));
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void addRentPayment(final RentPayment rentPayment) {
		this.rentPayments.add(rentPayment);
	}

	public Optional<Set<RentPayment>> getRentPayments() {
		return Optional.ofNullable(Collections.unmodifiableSet(this.rentPayments));
	}

	public BigDecimal getTotalEstimated() {
		return this.totalEstimated;
	}

	public BigDecimal getTotalCalculated() {
		return this.totalCalculated;
	}

	public void setTotalCalculated(final BigDecimal ammount) {
		this.totalCalculated = this.totalCalculated.add(ammount);
	}

	public BigDecimal getTotalPayment() {
		return this.totalPaid;
	}

	public BigDecimal paymentBaseCalculation() {

		if (ReturnStatus.PENDING.equals(this.getReturnStatus()) || ReturnStatus.INCOMPLETE.equals(this.getReturnStatus())) {
			return this.totalEstimated;
		}

		return this.totalCalculated;
	}

	public BigDecimal getTotalToPay() {

		if (BigDecimalUtil.isGraterThanOrEqual(this.totalPaid, this.paymentBaseCalculation())) {
			return BigDecimal.ZERO;
		}

		return this.paymentBaseCalculation().subtract(this.totalPaid);
	}

	public BigDecimal totalToRefund() {

		if (BigDecimalUtil.isLessThanOrEqual(this.totalPaid, this.paymentBaseCalculation())) {
			return BigDecimal.ZERO;
		}

		return this.totalPaid.subtract(this.paymentBaseCalculation());
	}

	public void calculateTotalEstimated() {
		this.totalEstimated = this.rentItems.stream().map(RentItem::getPlannedTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setTotalPaid(final BigDecimal paymentValue) {
		this.totalPaid = this.totalPaid.add(paymentValue);
	}

	public void setPaymentStatus() {

		if (LoadStatus.COMPLETE.equals(this.loadStatus) && ReturnStatus.COMPLETE.equals(this.returnStatus)
				&& BigDecimalUtil.isZero(this.totalCalculated)) {
			this.paymentStatus = PaymentStatus.COMPLETE;
			return;
		}

		if (BigDecimalUtil.isZero(this.totalPaid)) {
			this.paymentStatus = PaymentStatus.PENDING;
			return;
		}

		if (BigDecimalUtil.isLessThan(this.totalPaid, this.paymentBaseCalculation())) {
			this.paymentStatus = PaymentStatus.INCOMPLETE;
			return;
		}

		if (BigDecimalUtil.isEqual(this.totalPaid, this.paymentBaseCalculation())
				&& BigDecimalUtil.isEqual(this.getTotalToPay(), this.totalToRefund())) {
			this.paymentStatus = PaymentStatus.COMPLETE;
			return;
		}

		this.paymentStatus = PaymentStatus.OVER_PAYMENT;
	}

	public void setTotalEstimated(final BigDecimal totalEstimated) {
		this.totalEstimated = totalEstimated;
	}

	public void setLoadingStatus() {
		final boolean allMatch = this.rentItems.stream().allMatch(rentItem -> LoadStatus.COMPLETE.equals(rentItem.getLoadStatus()));

		if (allMatch) {
			this.loadStatus = LoadStatus.COMPLETE;
			return;
		}

		this.loadStatus = LoadStatus.INCOMPLETE;
	}

	public LoadStatus getLoadStatus() {
		return this.loadStatus;
	}

	public void setLoadStatus(final LoadStatus loadStatus) {
		this.loadStatus = loadStatus;
	}

	public void setReturnStatus() {
		final boolean allMatch = this.rentItems.stream().allMatch(rentItem -> ReturnStatus.COMPLETE.equals(rentItem.getReturnStatus()));

		if (allMatch) {
			this.returnStatus = ReturnStatus.COMPLETE;
			return;
		}

		this.returnStatus = ReturnStatus.INCOMPLETE;
	}

	public void setReturnStatus(final ReturnStatus returnStatus) {
		this.returnStatus = returnStatus;
	}

	public ReturnStatus getReturnStatus() {
		return this.returnStatus;
	}

	public Optional<Set<Guide>> getGuides() {
		return Optional.ofNullable(Collections.unmodifiableSet(this.guides));
	}

	public void addGuide(final Guide guide) {
		this.guides.add(guide);
	}

	public void refund(final BigDecimal paymentValue) {
		this.totalPaid = this.totalPaid.subtract(paymentValue);
	}

	public void setPaymentStatus(final PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public RentStatus getRentStatus() {
		return this.rentStatus;
	}

	public void closeRentStatus() {
		if (ReturnStatus.COMPLETE.equals(this.returnStatus) && PaymentStatus.COMPLETE.equals(this.paymentStatus)) {
			this.rentStatus = RentStatus.CLOSED;
		}
	}

	public void cleanLists() {
		this.rentItems = new HashSet<>();
		this.rentPayments = new HashSet<>();
		this.guides = new HashSet<>();
	}
}
