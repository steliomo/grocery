/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */

public class SalePaymentDTO extends GenericDTO {

	private String saleUuid;

	private BigDecimal paymentValue;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate paymentDate;

	public String getSaleUuid() {
		return this.saleUuid;
	}

	public void setSaleUuid(final String saleUuid) {
		this.saleUuid = saleUuid;
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
