/**
 *
 */
package mz.co.grocery.integ.resources.rent;

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

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.service.ReturnGuideIssuer;
import mz.co.grocery.core.application.guide.service.TransportGuideIssuer;
import mz.co.grocery.core.application.rent.in.MakeRentPaymentUseCase;
import mz.co.grocery.core.application.rent.in.RentUseCase;
import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.application.report.ReportGeneratorPort;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.rent.dto.RentDTO;
import mz.co.grocery.integ.resources.rent.dto.RentPaymentDTO;
import mz.co.grocery.integ.resources.rent.dto.RentReport;
import mz.co.grocery.integ.resources.rent.dto.RentsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Path("rents")
@WebAdapter
public class RentResource extends AbstractResource {

	@Inject
	private RentPort rentPort;

	@Inject
	private RentUseCase rentUseCase;

	@Inject
	private MakeRentPaymentUseCase makeRentPaymentUseCase;

	@Inject
	@BeanQualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Inject
	@BeanQualifier(ReturnGuideIssuer.NAME)
	private GuideIssuer returnGuideIssuer;

	@Inject
	private ReportGeneratorPort reportGeneratorPort;

	@Autowired
	private DTOMapper<RentDTO, Rent> rentMapper;

	@Autowired
	private DTOMapper<RentPaymentDTO, RentPayment> rentPaymentMapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rent(final RentDTO rentDTO) throws BusinessException {

		Rent rent = this.rentMapper.toDomain(rentDTO);

		rent = this.rentUseCase.rent(this.getContext(), rent);

		return Response.ok(this.rentMapper.toDTO(rent)).build();
	}

	@Path("pending-payments-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findPendingPaymentRentsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentPort.findPendingPaymentRentsByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.rentMapper)).build();
	}

	@Path("payments")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makePayment(final RentPaymentDTO rentPaymentDTO) throws BusinessException {
		RentPayment rentPayment = this.rentPaymentMapper.toDomain(rentPaymentDTO);

		rentPayment = this.makeRentPaymentUseCase.makeRentPayment(this.getContext(), rentPayment);

		return Response.ok(this.rentPaymentMapper.toDTO(rentPayment)).build();
	}

	@Path("fetch-rents-with-pending-or-incomplete-rent-item-to-load-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(@PathParam("customerUuid") final String customerUuid)
			throws BusinessException {

		final List<Rent> rents = this.rentPort.fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.rentMapper)).build();
	}

	@Path("fetch-rents-with-pending-or-incomplete-rent-item-to-return-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(@PathParam("customerUuid") final String customerUuid)
			throws BusinessException {

		final List<Rent> rents = this.rentPort.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.rentMapper)).build();
	}

	@Path("issue-quotation")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueQuotation(final RentDTO rentDTO) throws BusinessException {
		final Rent rent = this.rentMapper.toDomain(rentDTO);

		final RentReport rentReport = new RentReport(rent);

		this.reportGeneratorPort.createPdfReport(rentReport);

		return Response.ok(rentReport).build();
	}

	@Path("fetch-rents-with-issued-guides-by-type-and-customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithIssuedGuidesByTypeAndCustomer(@QueryParam("guideType") final GuideType guideType,
			@QueryParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentPort.fetchRentsWithIssuedGuidesByTypeAndCustomer(guideType, customerUuid);

		return Response.ok(new RentsDTO(rents, this.rentMapper)).build();
	}

	@Path("fetch-rents-with-payments-by-customer/{customerUuid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchRentsWithPaymentsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Rent> rents = this.rentPort.fetchRentsWithPaymentsByCustomer(customerUuid);

		return Response.ok(new RentsDTO(rents, this.rentMapper)).build();
	}
}
