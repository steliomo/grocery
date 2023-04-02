/**
 *
 */
package mz.co.grocery.core.contract.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.contract.dao.ContractDAO;
import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ContractServiceImpl.NAME)
public class ContractServiceImpl extends AbstractService implements ContractService {

	public static final String NAME = "mz.co.grocery.core.contract.service.ContractServiceImpl";

	private static final int THIRTY_DAYS = 30;

	@Inject
	private ContractDAO contractDAO;

	@Inject
	private ApplicationTranslator translator;

	@Override
	public Contract celebrateContract(final UserContext context, final Contract contract) throws BusinessException {

		if (contract.getDays() < ContractServiceImpl.THIRTY_DAYS) {
			throw new BusinessException(this.translator.getTranslation("contract.cannot.be.less.than.30.days"));
		}

		contract.setReferencePaymentDate();

		this.contractDAO.create(context, contract);

		return contract;
	}
}
