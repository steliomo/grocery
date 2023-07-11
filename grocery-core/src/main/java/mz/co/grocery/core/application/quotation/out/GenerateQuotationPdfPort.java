/**
 *
 */
package mz.co.grocery.core.application.quotation.out;

import java.time.LocalDateTime;

import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface GenerateQuotationPdfPort {
	String generatePdf(Quotation quotation, LocalDateTime quotationDateTime) throws BusinessException;
}
