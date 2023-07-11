/**
 *
 */
package mz.co.grocery.core.domain.rent;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */

public class RentPayment extends Domain {

	private Rent rent;

	private LocalDate paymentDate;

	private BigDecimal paymentValue;

	public Optional<Rent> getRent() {
		return Optional.ofNullable(this.rent);
	}

	public void setRent(final Rent rent) {
		this.rent = rent;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(final LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPaymentValue() {
		return this.paymentValue;
	}

	public void setPaymentValue(final BigDecimal paymentValue) {
		this.paymentValue = paymentValue;
	}

	public PaymentStatus getPaymentStatus() {
		return this.rent.getPaymentStatus();
	}
}
