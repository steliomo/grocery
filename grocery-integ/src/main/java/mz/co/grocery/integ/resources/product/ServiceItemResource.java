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

import mz.co.grocery.core.product.service.ServiceItemService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.product.dto.ServiceItemDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@Path("service-items")
@Service(ServiceItemResource.NAME)
public class ServiceItemResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.product.ServiceItemResource";

	@Inject
	private ServiceItemService serviceItemService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceItem(final ServiceItemDTO serviceItemDTO) throws BusinessException {
		this.serviceItemService.createServiceItem(this.getContext(), serviceItemDTO.get());
		return Response.ok(serviceItemDTO).build();
	}

}
