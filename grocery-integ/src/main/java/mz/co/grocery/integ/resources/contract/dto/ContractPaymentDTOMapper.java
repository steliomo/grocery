/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ContractPaymentDTOMapper extends AbstractDTOMapper<ContractPaymentDTO, ContractPayment>
implements DTOMapper<ContractPaymentDTO, ContractPayment> {

	private DTOMapper<ContractDTO, Contract> contractMapper;

	public ContractPaymentDTOMapper(final DTOMapper<ContractDTO, Contract> contractMapper) {
		this.contractMapper = contractMapper;
	}

	@Override
	public ContractPaymentDTO toDTO(final ContractPayment domain) {
		final ContractPaymentDTO dto = new ContractPaymentDTO();

		domain.getContract().ifPresent(contract -> dto.setContractDTO(this.contractMapper.toDTO(contract)));
		dto.setPaymentDate(domain.getPaymentDate());

		return this.toDTO(dto, domain);
	}

	@Override
	public ContractPayment toDomain(final ContractPaymentDTO dto) {
		final ContractPayment domain = new ContractPayment();

		dto.getContractDTO().ifPresent(contract -> domain.setContract(this.contractMapper.toDomain(contract)));
		domain.setPaymentDate(dto.getPaymentDate());

		return this.toDomain(dto, domain);
	}

}
