/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

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

import mz.co.grocery.persistence.sale.repository.SalePaymentRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@NamedQueries({
	@NamedQuery(name = SalePaymentRepository.QUERY_NAME.findDebtCollectionsByUnitAndPeriod, query = SalePaymentRepository.QUERY.findDebtCollectionsByUnitAndPeriod)
})

@Entity
@Table(name = "SALE_PAYMENTS")
public class SalePaymentEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID", nullable = false)
	private SaleEntity sale;

	@NotNull
	@Column(name = "PAYMENT_VALUE", nullable = false)
	private BigDecimal paymentValue;

	@NotNull
	@Column(name = "PAYMENT_DATE", nullable = false)
	private LocalDate paymentDate;

	public SalePaymentEntity(final SaleEntity sale, final BigDecimal paymentValue, final LocalDate paymentDate) {
		this.sale = sale;
		this.paymentValue = paymentValue;
		this.paymentDate = paymentDate;
	}

	public SalePaymentEntity() {
	}

	public SaleEntity getSale() {
		return this.sale;
	}

	public void setSale(final SaleEntity sale) {
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
