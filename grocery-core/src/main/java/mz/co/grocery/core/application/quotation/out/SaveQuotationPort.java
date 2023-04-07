/**
 *
 */
package mz.co.grocery.core.application.quotation.out;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SaveQuotationPort {

	Quotation save(UserContext context, Quotation quotation) throws BusinessException;

}
