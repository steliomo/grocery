/**
 *
 */
package mz.co.grocery.core.application.contract.in;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface CelebrateContractUseCase {

	Contract celebrateContract(UserContext context, Contract contract) throws BusinessException;

}
