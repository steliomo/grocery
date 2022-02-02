/**
 *
 */
package mz.co.grocery.integ.resources.contract.dto;

import java.util.List;
import java.util.stream.Collectors;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.util.ApplicationTranslator;

/**
 * @author Stélio Moiane
 *
 */
public class ContractsDTO {

	private List<Contract> contracts;

	private ApplicationTranslator translator;

	public ContractsDTO() {
	}

	public ContractsDTO(final List<Contract> contracts, final ApplicationTranslator translator) {
		this.contracts = contracts;
		this.translator = translator;
	}

	public List<ContractDTO> getContracts() {
		return this.contracts.stream().map(contract -> new ContractDTO(contract, this.translator)).collect(Collectors.toList());
	}
}
