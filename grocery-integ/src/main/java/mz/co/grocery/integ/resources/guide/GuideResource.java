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

import mz.co.grocery.core.application.guide.in.GuideIssuer;
import mz.co.grocery.core.application.guide.in.IssueGuideUseCase;
import mz.co.grocery.core.application.guide.service.DeliveryGuideIssuer;
import mz.co.grocery.core.application.guide.service.ReturnGuideIssuer;
import mz.co.grocery.core.application.guide.service.TransportGuideIssuer;
import mz.co.grocery.core.common.BeanQualifier;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.guide.dto.GuideDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("guides")
@WebAdapter
public class GuideResource extends AbstractResource {

	@Inject
	private IssueGuideUseCase guideUseCase;

	@Autowired
	@BeanQualifier(TransportGuideIssuer.NAME)
	private GuideIssuer transportGuideIssuer;

	@Autowired
	@BeanQualifier(ReturnGuideIssuer.NAME)
	private GuideIssuer returnGuideIssuer;

	@Autowired
	@BeanQualifier(DeliveryGuideIssuer.NAME)
	private GuideIssuer deliveryGuideIssuer;

	@Autowired
	private DTOMapper<GuideDTO, Guide> guideMapper;

	@Path("issue-transport-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueTransportGuide(final GuideDTO trasportGuideDTO) throws BusinessException {
		this.guideUseCase.setGuideIssuer(this.transportGuideIssuer);

		Guide guide = this.guideMapper.toDomain(trasportGuideDTO);

		guide = this.guideUseCase.issueGuide(this.getContext(), guide);

		return Response.ok(this.guideMapper.toDTO(guide)).build();
	}

	@Path("issue-return-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueReturnGuide(final GuideDTO returnGuideDTO) throws BusinessException {
		this.guideUseCase.setGuideIssuer(this.returnGuideIssuer);

		Guide guide = this.guideMapper.toDomain(returnGuideDTO);

		guide = this.guideUseCase.issueGuide(this.getContext(), guide);

		return Response.ok(this.guideMapper.toDTO(guide)).build();
	}

	@Path("issue-delivery-guide")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response issueDeliveryGuide(final GuideDTO deliveryGuideDTO) throws BusinessException {
		this.guideUseCase.setGuideIssuer(this.deliveryGuideIssuer);

		Guide guide = this.guideMapper.toDomain(deliveryGuideDTO);

		guide = this.guideUseCase.issueGuide(this.getContext(), guide);

		return Response.ok(this.guideMapper.toDTO(guide)).build();
	}
}
