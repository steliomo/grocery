/**
 *
 */
package mz.co.grocery.persistence.contract.entity;

import java.time.LocalDate;
import java.util.Optional;

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
public class ContractPaymentEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private ContractEntity contract;

	@NotNull
	@Column(name = "PAYMENT_DATE", nullable = false)
	private LocalDate paymentDate;

	@NotNull
	@Column(name = "REFERENCE_DATE", nullable = false)
	private LocalDate referenceDate;

	public Optional<ContractEntity> getContract() {
		return Optional.ofNullable(this.contract);
	}

	public void setContract(final ContractEntity contract) {
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

	public void setReferenceDate(final LocalDate referenceDate) {
		this.referenceDate = referenceDate;
	}
}
