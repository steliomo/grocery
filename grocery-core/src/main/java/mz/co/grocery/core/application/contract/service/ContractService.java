/**
 *
 */
package mz.co.grocery.core.application.contract.service;

import mz.co.grocery.core.application.contract.in.CelebrateContractUseCase;
import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class ContractService extends AbstractService implements CelebrateContractUseCase {

	private static final int THIRTY_DAYS = 30;

	private ContractPort contractPort;

	public ContractService(final ContractPort contractPort) {
		this.contractPort = contractPort;
	}

	@Override
	public Contract celebrateContract(final UserContext context, Contract contract) throws BusinessException {

		if (contract.getDays() < ContractService.THIRTY_DAYS) {
			throw new BusinessException("contract.cannot.be.less.than.30.days");
		}

		contract.setReferencePaymentDate();

		contract = this.contractPort.createContract(context, contract);

		return contract;
	}
}
