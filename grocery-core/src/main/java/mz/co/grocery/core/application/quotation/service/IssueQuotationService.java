/**
 *
 */
package mz.co.grocery.core.application.quotation.service;

import mz.co.grocery.core.application.quotation.in.IssueQuotationUseCase;
import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationItemPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationPort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
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

	public IssueQuotationService(final Clock clock, final SaveQuotationPort saveQuotationPort, final SaveQuotationItemPort saveQuotationItemPort,
			final GenerateQuotationPdfPort generateQuotationPdfPort) {
		this.clock = clock;
		this.saveQuotationPort = saveQuotationPort;
		this.saveQuotationItemPort = saveQuotationItemPort;
		this.generateQuotationPdfPort = generateQuotationPdfPort;
	}

	@Override
	public Quotation issueQuotation(final UserContext context, final Quotation quotation) throws BusinessException {

		if (!quotation.hasItems()) {
			throw new BusinessException("quotation.issue.has.no.items");
		}

		if (!quotation.getCustomer().isPresent()) {
			throw new BusinessException("quotation.issue.has.no.customer");
		}

		quotation.setIssueDate(this.clock.todayDate());
		quotation.setToPendingStatus();
		quotation.calculateTotal();

		this.saveQuotationPort.save(context, quotation);

		for (final QuotationItem quotationItem : quotation.getItems()) {
			quotationItem.setQuotation(quotation);

			this.saveQuotationItemPort.save(context, quotationItem);
		}

		final String name = this.generateQuotationPdfPort.generatePdf(quotation, this.clock.todayDateTime());
		quotation.setName(name);

		return quotation;
	}
}
