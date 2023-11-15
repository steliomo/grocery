/**
 *
 */
package mz.co.grocery.core.application.pos.in;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

public interface SendTableBillUseCase {

	Sale sendBill(Sale sale) throws BusinessException;

}
