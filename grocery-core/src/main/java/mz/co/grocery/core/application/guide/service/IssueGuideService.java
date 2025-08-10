/**
 *
 */
package mz.co.grocery.core.application.guide.service;

import java.util.HashSet;
import java.util.Set;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.grocery.core.domain.guide.GuideReport;
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

	private DocumentGeneratorPort reportGeneratorPort;

	private ApplicationTranslator translator;

	private GuideIssuer guideIssuer;

	public IssueGuideService(final ApplicationTranslator translator, final DocumentGeneratorPort reportGeneratorPort) {
		this.translator = translator;
		this.reportGeneratorPort = reportGeneratorPort;
	}

	@Override
	public Guide issueGuide(final UserContext userContext, Guide guide) throws BusinessException {

		if (this.guideIssuer == null) {
			throw new BusinessException("guide.issuer.must.be.indicated");
		}

		final Set<GuideItem> items = guide.getGuideItems().orElse(new HashSet<GuideItem>());

		guide = this.guideIssuer.issue(userContext, guide);

		final Document report = new GuideReport(guide, items, this.translator);

		guide.setFilename(report.getFilename());

		this.reportGeneratorPort.generatePdfDocument(report);

		return guide;
	}

	@Override
	public void setGuideIssuer(final GuideIssuer guideIssuer) {
		this.guideIssuer = guideIssuer;
	}
}
