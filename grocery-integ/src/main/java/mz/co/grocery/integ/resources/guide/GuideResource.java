/**
 *
 */
package mz.co.grocery.integ.resources.guide;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mz.co.grocery.core.application.report.ReportGeneratorPort;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideReport;
import mz.co.grocery.core.guide.service.DeliveryGuideIssuerImpl;
import mz.co.grocery.core.guide.service.GuideIssuer;
import mz.co.grocery.core.guide.service.GuideService;
import mz.co.grocery.core.guide.service.ReturnGuideIssuerImpl;
import mz.co.grocery.core.guide.service.TransportGuideIssuerImpl;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.guide.dto.GuideDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("guides")
@Service(GuideResource.NAME)
public class GuideResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.guide.GuideResource";

	@Inject
	private GuideService guideService;

	@Autowired
	@Qualifier(TransportGuideIssuerImpl.NAME)
	private GuideIssuer transportGuideIssuer;

	@Autowired
	@Qualifier(ReturnGuideIssuerImpl.NAME)
	private GuideIssuer returnGuideIssuer;

	@Autowired
	@Qualifier(DeliveryGuideIssuerImpl.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Inject
	private ApplicationTranslator translator;

	@Inject
	private ReportGeneratorPort reportGeneratorPort;

	@Path("issue-transport-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueTransportGuide(final GuideDTO trasportGuideDTO) throws BusinessException {
		this.guideService.setGuideIssuer(this.transportGuideIssuer);

		this.issueGuide(trasportGuideDTO);

		return Response.ok(trasportGuideDTO).build();
	}

	@Path("issue-return-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueReturnGuide(final GuideDTO returnGuideDTO) throws BusinessException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);

		this.issueGuide(returnGuideDTO);

		return Response.ok(returnGuideDTO).build();
	}

	@Path("issue-delivery-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueDeliveryGuide(final GuideDTO deliveryGuideDTO) throws BusinessException {
		this.guideService.setGuideIssuer(this.deliveryGuideIssuer);

		this.issueGuide(deliveryGuideDTO);

		return Response.ok(deliveryGuideDTO).build();
	}

	@Path("issue-guide-pdf")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueGuidePDF(final GuideDTO guideDTO) throws BusinessException {

		final GuideReport guideReport = new GuideReport(guideDTO.get(), this.translator);

		this.reportGeneratorPort.createPdfReport(guideReport);

		guideDTO.setFileName(guideReport.getFileName());

		return Response.ok(guideDTO).build();
	}

	private void issueGuide(final GuideDTO guideDTO) throws BusinessException {
		final Guide guide = this.guideService.issueGuide(this.getContext(), guideDTO.get());
		final GuideReport report = new GuideReport(guide, this.translator);
		guideDTO.setFileName(report.getFileName());
	}

}
