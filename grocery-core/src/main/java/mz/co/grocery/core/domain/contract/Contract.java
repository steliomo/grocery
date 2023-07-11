/**
 *
 */
package mz.co.grocery.core.domain.contract;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class Contract extends Domain {

	private ContractType contractType;

	private String description;

	private LocalDate startDate;

	private LocalDate endDate;

	private LocalDate referencePaymentDate;

	private BigDecimal monthlyPayment;

	private BigDecimal totalPaid;

	private Unit unit;

	private Customer customer;

	public Contract() {
		this.totalPaid = BigDecimal.ZERO;
	}

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

	public void calculateTotalPaid() {
		this.totalPaid = this.totalPaid.add(this.monthlyPayment);
	}

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public Optional<Customer> getCustomer() {
		return Optional.ofNullable(this.customer);
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Long getDays() {
		return ChronoUnit.DAYS.between(this.startDate, this.endDate);
	}

	public void setTotalPaid(final BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public void setReferencePaymentDate(final LocalDate referencePaymentDate) {
		this.referencePaymentDate = referencePaymentDate;
	}
}
