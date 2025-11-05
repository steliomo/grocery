/**
 *
 */
package mz.co.grocery.core.application.pos.in;

import mz.co.grocery.core.domain.pos.Debt;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface PayDeptUseCase {

	Debt pay(UserContext userContext, Debt dept) throws BusinessException;

}
