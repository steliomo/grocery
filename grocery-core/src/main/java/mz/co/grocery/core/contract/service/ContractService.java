/**
 *
 */
package mz.co.grocery.core.contract.service;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ContractService {

	Contract celebrateContract(UserContext context, Contract contract) throws BusinessException;

}
