/**
 *
 */
package mz.co.grocery.core.application.sale.in;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RegistCreditSaleUseCase {

	Sale regist(final UserContext userContext, String saleUuid) throws BusinessException;

}
