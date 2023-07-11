/**
 *
 */
package mz.co.grocery.core.application.sale.out;

import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleItemPort {

	SaleItem createSaleItem(UserContext context, SaleItem saleItem) throws BusinessException;

	SaleItem updateSaleItem(UserContext userContext, SaleItem saleItem) throws BusinessException;

	SaleItem findByUuid(String uuid) throws BusinessException;
}
