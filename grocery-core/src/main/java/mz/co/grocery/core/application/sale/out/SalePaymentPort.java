/**
 *
 */
package mz.co.grocery.core.application.sale.out;

import mz.co.grocery.core.domain.sale.SalePayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SalePaymentPort {

	SalePayment createSalePayment(UserContext context, SalePayment salePayment) throws BusinessException;

}
