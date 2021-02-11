/**
 *
 */
package mz.co.grocery.integ.resources.product;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.service.ServiceDescriptionService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.product.dto.ServiceDescriptionDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("service-descriptions")
@Service(ServiceDescriptionResource.NAME)
public class ServiceDescriptionResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.product.ServiceDescriptionResource";

	@Inject
	private ServiceDescriptionService serviceDescriptionService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceDescription(final ServiceDescriptionDTO serviceDescriptionDTO) throws BusinessException {
		this.serviceDescriptionService.createServiceDescription(this.getContext(), serviceDescriptionDTO.get());
		return Response.ok(serviceDescriptionDTO).build();
	}
}
