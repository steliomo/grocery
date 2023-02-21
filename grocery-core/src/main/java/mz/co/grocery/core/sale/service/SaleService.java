/**
 *
 */
package mz.co.grocery.core.sale.service;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleService {

	Sale registSale(UserContext userContext, Sale sale) throws BusinessException;
}
