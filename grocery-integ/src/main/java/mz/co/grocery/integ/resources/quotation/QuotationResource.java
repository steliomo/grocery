/**
 *
 */
package mz.co.grocery.integ.resources.quotation;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.quotation.in.IssueQuotationUseCase;
import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.QuotationPort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.quotation.dto.QuotationDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("quotations")
@WebAdapter
public class QuotationResource extends AbstractResource {

	@Inject
	private IssueQuotationUseCase issueQuotationUseCase;

	@Inject
	private QuotationPort quotationPort;

	@Inject
	private GenerateQuotationPdfPort generateQuotationPdfPort;

	@Inject
	private Clock clock;

	@Autowired
	private DTOMapper<QuotationDTO, Quotation> quotationMapper;

	@Path("issue-quotation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response issueQuotation(final QuotationDTO quotationDTO) throws BusinessException {

		Quotation quotation = this.quotationMapper.toDomain(quotationDTO);

		quotation = this.issueQuotationUseCase.issueQuotation(this.getContext(), quotation);

		return Response.ok(this.quotationMapper.toDTO(quotation)).build();
	}

	@Path("fetch-quotations-by-customer/{customerUuid}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchQuotationsByCustomer(@PathParam("customerUuid") final String customerUuid) throws BusinessException {

		final List<Quotation> quotations = this.quotationPort.fetchQuotationsByCustomer(customerUuid);

		return Response.ok(quotations.stream().map(this.quotationMapper::toDTO).collect(Collectors.toList())).build();
	}

	@Path("re-issue-quotation")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reIssueQuotation(final QuotationDTO quotationDTO) throws BusinessException {

		final Quotation quotation = this.quotationMapper.toDomain(quotationDTO);

		final String name = this.generateQuotationPdfPort.generatePdf(quotation, this.clock.todayDateTime());
		quotation.setName(name);

		return Response.ok(this.quotationMapper.toDTO(quotation)).build();
	}
}
