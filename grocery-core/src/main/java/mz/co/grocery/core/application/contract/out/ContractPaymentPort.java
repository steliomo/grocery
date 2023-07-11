/**
 *
 */
package mz.co.grocery.core.application.contract.out;

import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ContractPaymentPort {

	ContractPayment createContractPayment(UserContext userContext, ContractPayment contractPayment) throws BusinessException;

}
