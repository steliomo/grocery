/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import mz.co.grocery.core.contract.model.ContractPayment;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.msaude.boot.frameworks.util.LocalDateAdapter;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPaymentDTO extends GenericDTO<ContractPayment> {

	private ContractDTO contractDTO;

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate paymentDate;

	@Override
	public void mapper(final ContractPayment contractPayment) {
		this.contractDTO = new ContractDTO(contractPayment.getContract());
		this.paymentDate = contractPayment.getPaymentDate();
	}

	@Override
	public ContractPayment get() {
		final ContractPayment contractPayment = this.get(new ContractPayment());
		contractPayment.setContract(this.contractDTO.get());
		contractPayment.setPaymentDate(this.paymentDate);

		return contractPayment;
	}

	public ContractDTO getContractDTO() {
		return this.contractDTO;
	}

	public LocalDate getPaymentDate() {
		return this.paymentDate;
	}
}
