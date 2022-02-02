/**
 *
 */
package mz.co.grocery.core.contract.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@Entity
@Table(name = "CONTRACT_PAYMENTS")
public class ContractPayment extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contract contract;

	@NotNull
	@Column(name = "PAYMENT_DATE", nullable = false)
	private LocalDate paymentDate;

	@NotNull
	@Column(name = "REFERENCE_DATE", nullable = false)
	private LocalDate referenceDate;

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(final Contract contract) {
		this.contract = contract;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(final LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public LocalDate getReferenceDate() {
		return this.referenceDate;
	}

	public void setReferenceDate() {
		this.referenceDate = this.contract.getReferencePaymentDate();
	}
}
