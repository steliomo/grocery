/**
 *
 */
package mz.co.grocery.core.application.quotation.service;

import mz.co.grocery.core.application.quotation.in.IssueQuotationUseCase;
import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationItemPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.util.Clock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class IssueQuotationService extends AbstractService implements IssueQuotationUseCase {

	private Clock clock;
	private SaveQuotationPort saveQuotationPort;
	private SaveQuotationItemPort saveQuotationItemPort;
	private GenerateQuotationPdfPort generateQuotationPdfPort;
	private PaymentService paymentService;

	public IssueQuotationService(final Clock clock, final SaveQuotationPort saveQuotationPort, final SaveQuotationItemPort saveQuotationItemPort,
			final GenerateQuotationPdfPort generateQuotationPdfPort, final PaymentService paymentService) {
		this.clock = clock;
		this.saveQuotationPort = saveQuotationPort;
		this.saveQuotationItemPort = saveQuotationItemPort;
		this.generateQuotationPdfPort = generateQuotationPdfPort;
		this.paymentService = paymentService;
	}

	@Override
	public Quotation issueQuotation(final UserContext context, final Quotation quotation) throws BusinessException {

		if (!quotation.hasItems()) {
			throw new BusinessException("quotation.issue.has.no.items");
		}

		quotation.setIssueDate(this.clock.todayDate());
		quotation.setToPendingStatus();

		this.saveQuotationPort.save(context, quotation);

		for (final QuotationItem quotationItem : quotation.getItems()) {
			quotationItem.setQuotation(quotation);

			this.saveQuotationItemPort.save(context, quotationItem);
		}

		this.generateQuotationPdfPort.generatePdf(quotation);
		this.paymentService.debitTransaction(context, quotation.getUnit().getUuid());

		return quotation;
	}
}
