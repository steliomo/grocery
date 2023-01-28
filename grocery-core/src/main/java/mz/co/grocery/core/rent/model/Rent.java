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

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = RentDAO.QUERY_NAME.findPendinPaymentsByCustomer, query = RentDAO.QUERY.findPendinPaymentsByCustomer),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchPendingOrIncompleteRentItemToLoadByCustomer, query = RentDAO.QUERY.fetchPendingOrIncompleteRentItemToLoadByCustomer),
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer, query = RentDAO.QUERY.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer) })
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
		return Collections.unmodifiableSet(this.rentItems);
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void addRentPayment(final RentPayment rentPayment) {
		this.rentPayments.add(rentPayment);
	}

	public Set<RentPayment> getRentPayments() {
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

	public BigDecimal getTotalToPay() {
		if (this.totalEstimated.compareTo(this.totalCalculated) == BigDecimal.ONE.intValue()) {
			return this.totalEstimated.subtract(this.totalPaid);
		} else if (this.totalEstimated.compareTo(this.totalCalculated) == BigDecimal.ZERO.intValue()) {
			return this.totalEstimated.subtract(this.totalPaid);
		}
		return this.totalCalculated.subtract(this.totalPaid);
	}

	public void calculateTotalEstimated() {
		this.totalEstimated = this.rentItems.stream().map(RentItem::getPlannedTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setTotalPaid(final BigDecimal paymentValue) {
		this.totalPaid = this.totalPaid.add(paymentValue);
	}

	public void setPaymentStatus() {
		if (this.totalPaid.compareTo(BigDecimal.ZERO) == BigDecimal.ZERO.intValue()) {
			this.paymentStatus = PaymentStatus.PENDING;
		} else if (this.totalPaid.compareTo(this.getTotalToPay()) == BigDecimal.ONE.negate().intValue()) {
			this.paymentStatus = PaymentStatus.INCOMPLETE;
		} else if (BigDecimal.ZERO.compareTo(this.getTotalToPay()) == BigDecimal.ZERO.intValue()) {
			this.paymentStatus = PaymentStatus.COMPLETE;
		} else {
			this.paymentStatus = PaymentStatus.OVER_PAYMENT;
		}
	}

	public void setTotalEstimated(final BigDecimal totalEstimated) {
		this.totalEstimated = totalEstimated;
	}
}
