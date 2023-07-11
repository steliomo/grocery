/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import java.time.LocalDate;
import java.util.Optional;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPaymentDTO extends GenericDTO {

	private ContractDTO contractDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate paymentDate;

	public Optional<ContractDTO> getContractDTO() {
		return Optional.ofNullable(this.contractDTO);
	}

	public void setContractDTO(final ContractDTO contractDTO) {
		this.contractDTO = contractDTO;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(final LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
}
