/**
 *
 */
package mz.co.grocery.core.domain.contract;

import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPayment extends Domain {

	private Contract contract;

	private LocalDate paymentDate;

	private LocalDate referenceDate;

	public Optional<Contract> getContract() {
		return Optional.ofNullable(this.contract);
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

	public void setReferenceDate(final LocalDate referenceDate) {
		this.referenceDate = referenceDate;
	}
}
