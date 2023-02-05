/**
 *
 */
package mz.co.grocery.integ.resources.rent;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mz.co.grocery.core.rent.model.Guide;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.service.GuideIssuer;
import mz.co.grocery.core.rent.service.GuideService;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentQueryService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.grocery.core.rent.service.ReturnGuideIssuer;
import mz.co.grocery.core.rent.service.TransportGuideIssuer;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.rent.dto.GuideDTO;
import mz.co.grocery.integ.resources.rent.dto.GuideReport;
import mz.co.grocery.integ.resources.rent.dto.RentDTO;
import mz.co.grocery.integ.resources.rent.dto.RentPaymentDTO;
import mz.co.grocery.integ.resources.rent.dto.RentReport;
import mz.co.grocery.integ.resources.rent.dto.RentsDTO;
import mz.co.grocery.integ.resources.util.FileGeneratorUtil;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import net.sf.jasperreports.engine.JRException;

/**
 * @author Stélio Moiane
 *
 */

@Path("rents")
@Service(RentResource.NAME)
public class RentResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.rent.RentResource";

	@Inject
	private RentService rentService;

	@Inject
	private RentQueryService rentQueryService;

	@Inject
	private RentPaymentService rentPaymentService;

	@Inject
	private GuideService guideService;

	@Autowired
	@Qualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Autowired
	@Qualifier(ReturnGuideIssuer.NAME)
	private GuideIssuer returnGuideIssuer;

	@Inject
	private ApplicationTranslator translator;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rent(final RentDTO rentDTO) throws BusinessException {

		this.rentService.rent(this.getContext(), rentDTO.get());

		return Response.ok(rentDTO).build();
	}

	@Path("pending-payments-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPendingPaymentRentsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentQueryService.findPendingPaymentRentsByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.translator)).build();
	}

	@Path("payments")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makePayment(final RentPaymentDTO rentPaymentDTO) throws BusinessException {

		this.rentPaymentService.makeRentPayment(this.getContext(), rentPaymentDTO.get());

		return Response.ok(rentPaymentDTO).build();
	}

	@Path("fetch-rents-with-pending-or-incomplete-rent-item-to-load-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(@PathParam("customerUuid") final String customerUuid)
			throws BusinessException {

		final List<Rent> rents = this.rentQueryService.fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.translator)).build();
	}

	@Path("fetch-rents-with-pending-or-incomplete-rent-item-to-return-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(@PathParam("customerUuid") final String customerUuid)
			throws BusinessException {

		final List<Rent> rents = this.rentQueryService.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.translator)).build();
	}

	@Path("issue-quotation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueQuotation(final RentDTO rentDTO) throws BusinessException, JRException, IOException {
		final Rent rent = rentDTO.get();
		final RentReport rentReport = new RentReport(rent);

		FileGeneratorUtil.generatePdf(RentReport.REPORT_XML_NAME, rentReport.getParameters(), rentReport.getRentItemsReport(), rentReport.getName());

		return Response.ok(rentReport).build();
	}

	@Path("issue-transport-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueTransportGuide(final GuideDTO guideDTO) throws BusinessException {
		this.guideService.setGuideIssuer(this.transportGuideIssuer);

		final Guide guide = this.guideService.issueGuide(this.getContext(), guideDTO.get());

		guideDTO.setIssueDate(guide.getIssueDate());
		guideDTO.setId(guide.getId());

		this.guidePdfGenerator(guideDTO);

		return Response.ok(guideDTO).build();
	}

	@Path("issue-guide-pdf")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueGuidePDF(final GuideDTO guideDTO) throws BusinessException {

		this.guidePdfGenerator(guideDTO);

		return Response.ok(guideDTO).build();
	}

	private void guidePdfGenerator(final GuideDTO guideDTO) throws BusinessException {
		final GuideReport guideReport = new GuideReport(guideDTO, this.translator);
		try {
			FileGeneratorUtil.generatePdf(GuideReport.REPORT_XML_NAME, guideReport.getParameters(), guideReport.getGuideItems(),
					guideReport.getFileName());
			guideDTO.setFileName(guideReport.getFileName());
		} catch (IOException | JRException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Path("issue-return-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueReturnGuide(final GuideDTO guideDTO) throws BusinessException, IOException, JRException {
		this.guideService.setGuideIssuer(this.returnGuideIssuer);

		final Guide guide = this.guideService.issueGuide(this.getContext(), guideDTO.get());

		guideDTO.setIssueDate(guide.getIssueDate());
		guideDTO.setId(guide.getId());

		this.guidePdfGenerator(guideDTO);

		return Response.ok(guideDTO).build();
	}

	@Path("fetch-rents-with-issued-guides-by-type-and-customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithIssuedGuidesByTypeAndCustomer(@QueryParam("guideType") final GuideType guideType,
			@QueryParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentQueryService.fetchRentsWithIssuedGuidesByTypeAndCustomer(guideType, customerUuid);

		final RentsDTO rentsDTO = new RentsDTO(rents, this.translator);

		return Response.ok(rentsDTO).build();
	}

	@Path("fetch-rents-with-payments-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithPaymentsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentQueryService.fetchRentsWithPaymentsByCustomer(customerUuid);

		final RentsDTO rentsDTO = new RentsDTO(rents, this.translator);

		return Response.ok(rentsDTO).build();
	}
}
