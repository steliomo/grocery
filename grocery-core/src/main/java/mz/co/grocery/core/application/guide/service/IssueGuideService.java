/**
 *
 */
package mz.co.grocery.core.application.guide.service;

import java.util.HashSet;
import java.util.Set;

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.payment.in.PaymentUseCase;
import mz.co.grocery.core.application.report.ReportGeneratorPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideReport;
import mz.co.grocery.core.domain.report.Report;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@UseCase
public class IssueGuideService extends AbstractService implements IssueGuideUseCase {

	private PaymentUseCase paymentUseCase;

	private ReportGeneratorPort reportGeneratorPort;

	private ApplicationTranslator translator;

	private GuideIssuer guideIssuer;

	public IssueGuideService(final ApplicationTranslator translator, final ReportGeneratorPort reportGeneratorPort,
			final PaymentUseCase paymentUseCase) {
		this.translator = translator;
		this.reportGeneratorPort = reportGeneratorPort;
		this.paymentUseCase = paymentUseCase;
	}

	@Override
	public Guide issueGuide(final UserContext userContext, Guide guide) throws BusinessException {

		if (this.guideIssuer == null) {
			throw new BusinessException("guide.issuer.must.be.indicated");
		}

		final Set<GuideItem> items = guide.getGuideItems().orElse(new HashSet<GuideItem>());

		guide = this.guideIssuer.issue(userContext, guide);

		final Report report = new GuideReport(guide, items, this.translator);

		guide.setFilePath(report.getFilePath());

		this.reportGeneratorPort.createPdfReport(report);

		this.paymentUseCase.debitTransaction(userContext, guide.getUnit().getUuid());

		return guide;
	}

	@Override
	public void setGuideIssuer(final GuideIssuer guideIssuer) {
		this.guideIssuer = guideIssuer;
	}
}
