/**
 *
 */
package mz.co.grocery.persistence.rent.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.domain.rent.PaymentStatus;
import mz.co.grocery.persistence.rent.repository.RentPaymentRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({
	@NamedQuery(name = RentPaymentRepository.QUERY_NAME.findSalesByUnitAndPeriod, query = RentPaymentRepository.QUERY.findSalesByUnitAndPeriod),
	@NamedQuery(name = RentPaymentRepository.QUERY_NAME.findSalesByUnitAndMonthlyPeriod, query = RentPaymentRepository.QUERY.findSalesByUnitAndMonthlyPeriod) })
@Entity
@Table(name = "RENT_PAYMENTS")
public class RentPaymentEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ID", nullable = false)
	private RentEntity rent;

	@NotNull
	@Column(name = "PAYMENT_DATE", nullable = false)
	private LocalDate paymentDate;

	@NotNull
	@Column(name = "PAYMENT_VALUE", nullable = false)
	private BigDecimal paymentValue;

	public Optional<RentEntity> getRent() {
		return Optional.ofNullable(this.rent);
	}

	public void setRent(final RentEntity rent) {
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
