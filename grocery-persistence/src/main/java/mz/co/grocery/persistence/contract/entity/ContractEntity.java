/**
 *
 */
package mz.co.grocery.persistence.contract.entity;

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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.domain.contract.ContractType;
import mz.co.grocery.persistence.contract.repository.ContractRepository;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = ContractRepository.QUERY_NAME.findPendingContractsForPaymentByCustomer, query = ContractRepository.QUERY.findPendingContractsForPaymentByCustomer) })
@Entity
@Table(name = "CONTRACTS")
public class ContractEntity extends GenericEntity {

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
	private UnitEntity unit;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID", nullable = false)
	private CustomerEntity customer;

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

	public void setReferencePaymentDate(final LocalDate referencePaymentDate) {
		this.referencePaymentDate = referencePaymentDate;
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

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public Optional<UnitEntity> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final UnitEntity unit) {
		this.unit = unit;
	}

	public Optional<CustomerEntity> getCustomer() {
		return Optional.ofNullable(this.customer);
	}

	public void setCustomer(final CustomerEntity customer) {
		this.customer = customer;
	}
}
