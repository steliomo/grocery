/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */

public class SalePayment extends Domain {

	private Sale sale;

	private BigDecimal paymentValue;

	private LocalDate paymentDate;

	public SalePayment(final Sale sale, final BigDecimal paymentValue, final LocalDate paymentDate) {
		this.sale = sale;
		this.paymentValue = paymentValue;
		this.paymentDate = paymentDate;
	}

	public Optional<Sale> getSale() {
		return Optional.ofNullable(this.sale);
	}

	public void setSale(final Sale sale) {
		this.sale = sale;
	}

	public BigDecimal getPaymentValue() {
		return this.paymentValue;
	}

	public void setPaymentValue(final BigDecimal paymentValue) {
		this.paymentValue = paymentValue;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(final LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
}
