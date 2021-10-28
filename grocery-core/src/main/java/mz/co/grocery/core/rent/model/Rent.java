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
	@NamedQuery(name = RentDAO.QUERY_NAME.fetchPendingDevolutionsByCustomer, query = RentDAO.QUERY.fetchPendingDevolutionsByCustomer) })
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
	@Column(name = "TOTAL_RENT", nullable = false)
	private BigDecimal totalRent = BigDecimal.ZERO;

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

	public BigDecimal getTotalRent() {
		return this.totalRent;
	}

	public BigDecimal getTotalPayment() {
		return this.totalPaid;
	}

	public BigDecimal getTotalToPay() {
		return this.totalRent.subtract(this.totalPaid);
	}

	public void setPaymentStatus() {

		if (this.totalRent.compareTo(this.totalPaid) == BigDecimal.ZERO.intValue()) {
			this.paymentStatus = PaymentStatus.COMPLETE;
			return;
		}

		if (this.totalPaid.intValue() != BigDecimal.ZERO.intValue() && this.totalPaid.compareTo(this.totalRent) == -BigDecimal.ONE.intValue()) {
			this.paymentStatus = PaymentStatus.INCOMPLETE;
		}
	}

	public void setTotalRent() {
		this.totalRent = this.rentItems.stream().map(RentItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public void setTotalPaid(final BigDecimal paymentValue) {
		this.totalPaid = this.totalPaid.add(paymentValue);
	}
}