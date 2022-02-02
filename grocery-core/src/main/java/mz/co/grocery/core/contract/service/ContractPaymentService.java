/**
 *
 */
package mz.co.grocery.core.contract.service;

import mz.co.grocery.core.contract.model.ContractPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ContractPaymentService {

	ContractPayment performContractPayment(UserContext userContext, ContractPayment contractPayment) throws BusinessException;

}
