/**
 *
 */
package mz.co.grocery.core.rent.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({ @NamedQuery(name = RentPaymentDAO.QUERY_NAME.findSalesByUnitAndPeriod, query = RentPaymentDAO.QUERY.findSalesByUnitAndPeriod),
	@NamedQuery(name = RentPaymentDAO.QUERY_NAME.findSalesByUnitAndMonthlyPeriod, query = RentPaymentDAO.QUERY.findSalesByUnitAndMonthlyPeriod) })
@Entity
@Table(name = "RENT_PAYMENTS")
public class RentPayment extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ID", nullable = false)
	private Rent rent;

	@NotNull
	@Column(name = "PAYMENT_DATE", nullable = false)
	private LocalDate paymentDate;

	@NotNull
	@Column(name = "PAYMENT_VALUE", nullable = false)
	private BigDecimal paymentValue;

	public Rent getRent() {
		return this.rent;
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
