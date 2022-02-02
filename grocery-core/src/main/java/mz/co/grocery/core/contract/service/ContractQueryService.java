/**
 *
 */
package mz.co.grocery.core.contract.service;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ContractQueryService {

	List<Contract> findPendingContractsForPaymentByCustomerUuid(String customerUuid, LocalDate currentDate) throws BusinessException;
}
