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
public interface OpenTableUseCase {

	Sale openTable(UserContext context, Sale sale) throws BusinessException;

}
