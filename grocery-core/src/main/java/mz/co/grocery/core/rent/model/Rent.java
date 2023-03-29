/**
 *
 */
package mz.co.grocery.core.rent.model;

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
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.util.BigDecimalUtil;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = RentDAO.QUERY_NAME.findPendinPaymentsByCustomer, query = RentDAO.QUERY.findPendinPaymentsByCustomer),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchPendingOrIncompleteRentItemToLoadByCustomer, query = RentDAO.QUERY.fetchPendingOrIncompleteRentItemToLoadByCustomer),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer, query = RentDAO.QUERY.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchByUuid, query = RentDAO.QUERY.fetchByUuid),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchRentsWithIssuedGuidesByTypeAndCustomer, query = RentDAO.QUERY.fetchRentsWithIssuedGuidesByTypeAndCustomer),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchRentsWithPaymentsByCustomer, query = RentDAO.QUERY.fetchRentsWithPaymentsByCustomer) })
@Entity
@Table(name = "RENTS")
public class Rent extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Grocery unit;

	@NotNull
	@Column(name = "RENT_DATE", nullable = false)
	private LocalDate rentDate;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_STATUS", nullable = false, length = 15)
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(name = "LOAD_STATUS", nullable = false, length = 15)
	private LoadStatus loadStatus = LoadStatus.PENDING;

	@Enumerated(EnumType.STRING)
	@Column(name = "RETURN_STATUS", nullable = false, length = 15)
	private ReturnStatus returnStatus = ReturnStatus.PENDING;

	@NotNull
	@Column(name = "TOTAL_ESTIMATED", nullable = false)
	private BigDecimal totalEstimated = BigDecimal.ZERO;

	@NotNull
	@Column(name = "TOTAL_CALCULATED", nullable = false)
	private BigDecimal totalCalculated = BigDecimal.ZERO;

	@NotNull
	@Column(name = "TOTAL_PAID", nullable = false)
	private BigDecimal totalPaid = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rent")
	private final Set<RentItem> rentItems = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rent")
	private final Set<RentPayment> rentPayments = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rent")
	private Set<Guide> guides = new HashSet<>();

	public Grocery getUnit() {
		return this.unit;
	}

	public void setUnit(final Grocery unit) {
		this.unit = unit;
	}

	public void setRentDate(final LocalDate rentDate) {
		this.rentDate = rentDate;
	}

	public LocalDate getRentDate() {
		return this.rentDate;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void addRentItem(final RentItem rentItem) {
		this.rentItems.add(rentItem);
	}

	public Set<RentItem> getRentItems() {
		try {
			this.rentItems.size();
		} catch (final LazyInitializationException e) {
			return new HashSet<>();
		}

		return Collections.unmodifiableSet(this.rentItems);
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void addRentPayment(final RentPayment rentPayment) {
		this.rentPayments.add(rentPayment);
	}

	public Set<RentPayment> getRentPayments() {
		if (this.rentPayments == null) {
			return this.rentPayments;
		}

		try {
			this.rentPayments.size();
		} catch (final LazyInitializationException e) {
			return new HashSet<>();
		}

		return Collections.unmodifiableSet(this.rentPayments);
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

		if (ReturnStatus.COMPLETE.equals(this.getReturnStatus()) && BigDecimalUtil.isGraterThanOrEqual(this.totalCalculated, this.totalPaid)) {
			return this.totalCalculated;
		}

		if (BigDecimalUtil.isGraterThan(this.totalEstimated, this.totalCalculated)) {
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
		if (BigDecimalUtil.isGraterThan(this.totalPaid, this.totalCalculated) && ReturnStatus.COMPLETE.equals(this.getReturnStatus())) {
			return this.totalPaid.subtract(this.totalCalculated);
		}
		return BigDecimal.ZERO;
	}

	public void calculateTotalEstimated() {
		this.totalEstimated = this.rentItems.stream().map(RentItem::getPlannedTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setTotalPaid(final BigDecimal paymentValue) {
		this.totalPaid = this.totalPaid.add(paymentValue);
	}

	public void setPaymentStatus() {
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

	public LoadStatus getLoadingStatus() {
		return this.loadStatus;
	}

	public void setReturnStatus() {
		final boolean allMatch = this.rentItems.stream().allMatch(rentItem -> ReturnStatus.COMPLETE.equals(rentItem.getReturnStatus()));

		if (allMatch) {
			this.returnStatus = ReturnStatus.COMPLETE;
			return;
		}

		this.returnStatus = ReturnStatus.INCOMPLETE;
	}

	public ReturnStatus getReturnStatus() {
		return this.returnStatus;
	}

	public Set<Guide> getGuides() {

		if (this.guides == null) {
			this.guides = new HashSet<>();
			return this.guides;
		}

		try {
			this.guides.size();
		} catch (final LazyInitializationException e) {
			return new HashSet<>();
		}
		return Collections.unmodifiableSet(this.guides);
	}

	public void reCalculateEstimatedTotal(final BigDecimal estimated) {
		this.totalEstimated = this.totalEstimated.subtract(estimated);
	}
}
