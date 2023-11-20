/**
 *
 */
package mz.co.grocery.core.application.pos.out;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleNotifier {

	void registListner(SaleListner listner);

	void removeListner(SaleListner listner);

	Sale notify(UserContext context, Sale sale) throws BusinessException;
}
