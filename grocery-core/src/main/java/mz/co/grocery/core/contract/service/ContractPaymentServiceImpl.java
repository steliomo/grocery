/**
 *
 */
package mz.co.grocery.core.contract.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.contract.dao.ContractDAO;
import mz.co.grocery.core.contract.dao.ContractPaymentDAO;
import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.model.ContractPayment;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@Service(ContractPaymentServiceImpl.NAME)
public class ContractPaymentServiceImpl extends AbstractService implements ContractPaymentService {

	public static final String NAME = "mz.co.grocery.core.contract.service.ContractPaymentServiceImpl";

	@Inject
	private ContractPaymentDAO contractPaymentDAO;

	@Inject
	private ContractDAO contractDAO;

	@Inject
	private ApplicationTranslator translator;

	@Override
	public ContractPayment performContractPayment(final UserContext userContext, final ContractPayment contractPayment) throws BusinessException {

		final Contract contract = this.contractDAO.findById(contractPayment.getContract().getId());
		contractPayment.setContract(contract);
		contractPayment.setReferenceDate();

		if (!contract.getReferencePaymentDate().isEqual(contractPayment.getReferenceDate())) {
			throw new BusinessException(this.translator.getTranslation("reference.date.was.unexpected"));
		}

		contract.setTotalPaid();
		contract.setReferencePaymentDate();

		this.contractPaymentDAO.create(userContext, contractPayment);

		this.contractDAO.update(userContext, contract);

		return contractPayment;
	}
}
