/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.sale.model.SalePayment;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class SalePaymentDTO extends GenericDTO<SalePayment> {

	private String saleUuid;

	private BigDecimal paymentValue;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate paymentDate;

	public SalePaymentDTO() {
	}

	public SalePaymentDTO(final SalePayment salePayment) {
		super(salePayment);
	}

	@Override
	public void mapper(final SalePayment salePayment) {
		this.paymentValue = salePayment.getPaymentValue();
		this.paymentDate = salePayment.getPaymentDate();
	}

	@Override
	public SalePayment get() {
		final SalePayment salePayment = this.get(new SalePayment());
		salePayment.setPaymentValue(this.paymentValue);
		salePayment.setPaymentDate(this.paymentDate);

		return salePayment;
	}

	public String getSaleUuid() {
		return this.saleUuid;
	}

	public BigDecimal getPaymentValue() {
		return this.paymentValue;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}
}
