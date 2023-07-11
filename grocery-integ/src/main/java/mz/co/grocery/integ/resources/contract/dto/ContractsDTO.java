/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
public class ContractsDTO {

	private List<Contract> contracts;

	private DTOMapper<ContractDTO, Contract> contractMapper;

	public ContractsDTO() {
	}

	public ContractsDTO(final List<Contract> contracts, final DTOMapper<ContractDTO, Contract> contractMapper) {
		this.contracts = contracts;
		this.contractMapper = contractMapper;
	}

	public List<ContractDTO> getContracts() {
		return this.contracts.stream().map(contract -> this.contractMapper.toDTO(contract)).collect(Collectors.toList());
	}
}
