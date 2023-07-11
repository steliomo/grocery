/**
 *
 */
package mz.co.grocery.core.application.contract.in;

import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface PaymentContractUseCase {

	ContractPayment performContractPayment(UserContext userContext, ContractPayment contractPayment) throws BusinessException;

}
