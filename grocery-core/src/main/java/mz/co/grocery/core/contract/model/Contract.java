/**
 *
 */
package mz.co.grocery.core.contract.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.contract.dao.ContractDAO;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = ContractDAO.QUERY_NAME.findPendingContractsForPaymentByCustomer, query = ContractDAO.QUERY.findPendingContractsForPaymentByCustomer) })
@Entity
@Table(name = "CONTRACTS")
public class Contract extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "CONTRACT_TYPE", nullable = false, length = 50)
	private ContractType contractType;

	@NotEmpty
	@Column(name = "DESCRIPTION", nullable = false, length = 150)
	private String description;

	@NotNull
	@Column(name = "START_DATE", nullable = false)
	private LocalDate startDate;

	@NotNull
	@Column(name = "END_DATE", nullable = false)
	private LocalDate endDate;

	@NotNull
	@Column(name = "REFERENCE_PAYMENT_DATE", nullable = false)
	private LocalDate referencePaymentDate;

	@NotNull
	@Column(name = "MONTHLY_PAYMENT", nullable = false)
	private BigDecimal monthlyPayment;

	@NotNull
	@Column(name = "TOTAL_PAID", nullable = false)
	private BigDecimal totalPaid = BigDecimal.ZERO;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", nullable = false)
	private Grocery unit;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private Customer customer;

	public ContractType getContractType() {
		return this.contractType;
	}

	public void setContractType(final ContractType contractType) {
		this.contractType = contractType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getReferencePaymentDate() {
		return this.referencePaymentDate;
	}

	public void setReferencePaymentDate() {
		if (this.referencePaymentDate == null) {
			this.referencePaymentDate = this.startDate;
			return;
		}

		this.referencePaymentDate = this.referencePaymentDate.plusMonths(BigDecimal.ONE.longValue());
	}

	public BigDecimal getMonthlyPayment() {
		return this.monthlyPayment;
	}

	public void setMonthlyPayment(final BigDecimal monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public BigDecimal getTotalPaid() {
		return this.totalPaid;
	}

	public void setTotalPaid() {
		this.totalPaid = this.totalPaid.add(this.monthlyPayment);
	}

	public Grocery getUnit() {
		return this.unit;
	}

	public void setUnit(final Grocery unit) {
		this.unit = unit;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Long getDays() {
		return ChronoUnit.DAYS.between(this.startDate, this.endDate);
	}

	public void adjustEndDateDay() {
		this.endDate = this.endDate.withDayOfMonth(this.startDate.getDayOfMonth());
	}
}
