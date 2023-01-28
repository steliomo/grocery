/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentDTO extends GenericDTO<RentPayment> {

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate paymentDate;

	private BigDecimal paymentValue;

	private RentDTO rentDTO;

	public RentPaymentDTO() {
	}

	public RentPaymentDTO(final RentPayment rentPayment) {
		super(rentPayment);
	}

	@Override
	public void mapper(final RentPayment rentPayment) {
		this.paymentDate = rentPayment.getPaymentDate();
		this.paymentValue = rentPayment.getPaymentValue();
	}

	@Override
	public RentPayment get() {
		final RentPayment rentPayment = this.get(new RentPayment());
		rentPayment.setPaymentDate(this.paymentDate);
		rentPayment.setPaymentValue(this.paymentValue);
		rentPayment.setRent(this.rentDTO.get());

		return rentPayment;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}

	public BigDecimal getPaymentValue() {
		return this.paymentValue;
	}

	public RentDTO getRentDTO() {
		return this.rentDTO;
	}
}
