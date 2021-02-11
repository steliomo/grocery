/**
 *
 */
package mz.co.grocery.integ.resources.product;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.service.ServiceQueryService;
import mz.co.grocery.core.product.service.ServiceService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.product.dto.ServiceDTO;
import mz.co.grocery.integ.resources.product.dto.ServicesDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("services")
@Service(ServiceResource.NAME)
public class ServiceResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.product.ServiceResource";

	@Inject
	private ServiceService serviceService;

	@Inject
	private ServiceQueryService serviceQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createService(final ServiceDTO serviceDTO) throws BusinessException {
		this.serviceService.createService(this.getContext(), serviceDTO.get());
		return Response.ok(serviceDTO).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findServices(@QueryParam("currentPage") final int currentPage, @QueryParam("maxResult") final int maxResult)
			throws BusinessException {

		final List<mz.co.grocery.core.product.model.Service> services = this.serviceQueryService.findAllServices(currentPage, maxResult);
		final Long totalItems = this.serviceQueryService.countServices();

		final ServicesDTO servicesDTO = new ServicesDTO(services, totalItems);

		return Response.ok(servicesDTO).build();
	}

	@GET
	@Path("{serviceUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findProductByUuid(@PathParam("serviceUuid") final String serviceUuid) throws BusinessException {
		final mz.co.grocery.core.product.model.Service service = this.serviceQueryService.findServiceByUuid(serviceUuid);
		return Response.ok(new ServiceDTO(service)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateService(final ServiceDTO serviceDTO) throws BusinessException {
		this.serviceService.updateService(this.getContext(), serviceDTO.get());
		return Response.ok(serviceDTO).build();
	}
}
