/**
 *
 */
package mz.co.grocery.core.contract.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.contract.dao.ContractDAO;
import mz.co.grocery.core.contract.model.Contract;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */

@Service(ContractQueryServiceImpl.NAME)
public class ContractQueryServiceImpl implements ContractQueryService {

	public static final String NAME = "mz.co.grocery.core.contract.service.ContractQueryServiceImpl";

	@Inject
	private ContractDAO contractDAO;

	@Override
	public List<Contract> findPendingContractsForPaymentByCustomerUuid(final String customerUuid, final LocalDate currentDate)
			throws BusinessException {
		return this.contractDAO.findPendingContractsForPaymentByCustomerUuid(customerUuid, currentDate, EntityStatus.ACTIVE);
	}

}
