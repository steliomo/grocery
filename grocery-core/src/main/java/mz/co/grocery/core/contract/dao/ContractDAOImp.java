/**
 *
 */
package mz.co.grocery.core.contract.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ContractDAOImp.NAME)
public class ContractDAOImp extends GenericDAOImpl<Contract, Long> implements ContractDAO {

	public static final String NAME = "mz.co.grocery.core.contract.dao.ContractDAOImp";

	@Override
	public List<Contract> findPendingContractsForPaymentByCustomerUuid(final String customerUuid, final LocalDate currentDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ContractDAO.QUERY_NAME.findPendingContractsForPaymentByCustomer, new ParamBuilder()
				.add("customerUuid", customerUuid).add("currentDate", currentDate).add("entityStatus", entityStatus).process());
	}

}
