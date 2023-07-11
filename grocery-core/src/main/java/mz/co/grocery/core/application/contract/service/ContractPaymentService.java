/**
 *
 */
package mz.co.grocery.core.application.contract.service;

import mz.co.grocery.core.application.contract.in.PaymentContractUseCase;
import mz.co.grocery.core.application.contract.out.ContractPaymentPort;
import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class ContractPaymentService extends AbstractService implements PaymentContractUseCase {

	private ContractPaymentPort contractPaymentPort;

	private ContractPort contractPort;

	public ContractPaymentService(final ContractPaymentPort contractPaymentPort, final ContractPort contractPort) {
		this.contractPaymentPort = contractPaymentPort;
		this.contractPort = contractPort;
	}

	@Override
	public ContractPayment performContractPayment(final UserContext userContext, ContractPayment contractPayment) throws BusinessException {

		final Contract contract = this.contractPort.findcontractById(contractPayment.getContract().get().getId());
		contractPayment.setContract(contract);
		contractPayment.setReferenceDate();

		if (!contract.getReferencePaymentDate().isEqual(contractPayment.getReferenceDate())) {
			throw new BusinessException("reference.date.was.unexpected");
		}

		contract.calculateTotalPaid();
		contract.setReferencePaymentDate();

		contractPayment = this.contractPaymentPort.createContractPayment(userContext, contractPayment);

		this.contractPort.updateContract(userContext, contract);

		return contractPayment;
	}
}
