/**
 *
 */
package mz.co.grocery.core.guide.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.file.model.Report;
import mz.co.grocery.core.file.service.FileGeneratorService;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideReport;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(GuideServiceImpl.NAME)
public class GuideServiceImpl extends AbstractService implements GuideService {

	public static final String NAME = "mz.co.grocery.core.rent.service.GuideServiceImpl";

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private PaymentService paymentService;

	@Inject
	private FileGeneratorService fileGeneratorService;

	private GuideIssuer guideIssuer;

	@Override
	public Guide issueGuide(final UserContext userContext, final Guide guide) throws BusinessException {

		if (this.guideIssuer == null) {
			throw new BusinessException(this.translator.getTranslation("guide.issuer.must.be.indicated"));
		}

		this.guideIssuer.issue(userContext, guide);

		final Report report = new GuideReport(guide, this.translator);

		this.fileGeneratorService.createPdfReport(report);
		this.paymentService.debitTransaction(userContext, guide.getUnit().getUuid());

		return guide;
	}

	@Override
	public void setGuideIssuer(final GuideIssuer guideIssuer) {
		this.guideIssuer = guideIssuer;
	}

}
