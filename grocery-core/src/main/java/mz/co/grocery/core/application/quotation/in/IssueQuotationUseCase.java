/**
 *
 */
package mz.co.grocery.core.application.quotation.in;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface IssueQuotationUseCase {

	Quotation issueQuotation(UserContext userContext, Quotation quotation) throws BusinessException;

}
