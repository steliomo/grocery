/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentDTO extends GenericDTO {

	private RentDTO rentDTO;

	private BigDecimal paymentValue;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate paymentDate;

	public Optional<RentDTO> getRentDTO() {
		return Optional.ofNullable(this.rentDTO);
	}

	public void setRentDTO(final RentDTO rentDTO) {
		this.rentDTO = rentDTO;
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
