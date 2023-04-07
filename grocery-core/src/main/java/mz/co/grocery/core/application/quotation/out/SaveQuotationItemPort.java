/**
 *
 */
package mz.co.grocery.core.application.quotation.out;

import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaveQuotationItemPort {

	QuotationItem save(UserContext context, QuotationItem quotationItem) throws BusinessException;

}
