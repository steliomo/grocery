/**
 *
 */
package mz.co.grocery.core.application.contract.out;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ContractPort {

	Contract createContract(UserContext context, Contract contract) throws BusinessException;

	Contract updateContract(UserContext userContext, Contract contract) throws BusinessException;

	List<Contract> findPendingContractsForPaymentByCustomerUuid(String customerUuid, LocalDate currentDate) throws BusinessException;

	Contract findcontractById(Long contractId) throws BusinessException;
}
