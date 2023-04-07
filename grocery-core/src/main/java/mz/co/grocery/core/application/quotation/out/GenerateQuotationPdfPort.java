/**
 *
 */
package mz.co.grocery.core.application.quotation.out;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface GenerateQuotationPdfPort {
	void generatePdf(Quotation quotation) throws BusinessException;
}
