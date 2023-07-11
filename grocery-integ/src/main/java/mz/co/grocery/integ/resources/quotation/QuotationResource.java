/**
 *
 */
package mz.co.grocery.integ.resources.quotation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.quotation.in.IssueQuotationUseCase;
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
}
