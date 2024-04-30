/**
 *
 */
package mz.co.grocery.core.application.pos.in;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface CancelTableUseCase {

	Sale cancel(UserContext context, Sale table) throws BusinessException;

}
