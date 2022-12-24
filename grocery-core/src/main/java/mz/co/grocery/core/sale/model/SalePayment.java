/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;
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
@Table(name = "SALE_PAYMENTS")
public class SalePayment extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID", nullable = false)
	private Sale sale;

	@NotNull
	@Column(name = "PAYMENT_VALUE", nullable = false)
	private BigDecimal paymentValue;

	@NotNull
	@Column(name = "PAYMENT_DATE", nullable = false)
	private LocalDate paymentDate;

	public SalePayment(final Sale sale, final BigDecimal paymentValue, final LocalDate paymentDate) {
		this.sale = sale;
		this.paymentValue = paymentValue;
		this.paymentDate = paymentDate;
	}

	public SalePayment() {
	}

	public Sale getSale() {
		return this.sale;
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
