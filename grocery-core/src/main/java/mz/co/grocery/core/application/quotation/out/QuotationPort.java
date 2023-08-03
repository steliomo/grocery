/**
 *
 */
package mz.co.grocery.core.application.quotation.out;

import java.util.List;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface QuotationPort {

	List<Quotation> fetchQuotationsByCustomer(String customerUuid) throws BusinessException;

}
