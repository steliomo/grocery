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

import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.grocery.core.product.service.ServiceDescriptionQueryService;
import mz.co.grocery.core.product.service.ServiceDescriptionService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.product.dto.ServiceDescriptionDTO;
import mz.co.grocery.integ.resources.product.dto.ServiceDescriptionsDTO;
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

	@Inject
	private ServiceDescriptionQueryService serviceDescriptionQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceDescription(final ServiceDescriptionDTO serviceDescriptionDTO) throws BusinessException {
		this.serviceDescriptionService.createServiceDescription(this.getContext(), serviceDescriptionDTO.get());

		return Response.ok(serviceDescriptionDTO).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllServiceDescriptions(@QueryParam("currentPage") final int currentPage, @QueryParam("maxResult") final int maxResult)
			throws BusinessException {

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionQueryService.findAllServiceDescriptions(currentPage, maxResult);
		final Long totalServiceDescriptions = this.serviceDescriptionQueryService.countServiceDescriptions();

		return Response.ok(new ServiceDescriptionsDTO(serviceDescriptions, totalServiceDescriptions)).build();
	}

	@GET
	@Path("{serviceDescriptionUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceDescriptionByUuid(@PathParam("serviceDescriptionUuid") final String serviceDescriptionUuid)
			throws BusinessException {

		final ServiceDescription serviceDescriptions = this.serviceDescriptionQueryService.fetchServiceDescriptionByUuid(serviceDescriptionUuid);

		return Response.ok(new ServiceDescriptionDTO(serviceDescriptions)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateServiceDescription(final ServiceDescriptionDTO serviceDescriptionDTO) throws BusinessException {
		this.serviceDescriptionService.updateServiceDescription(this.getContext(), serviceDescriptionDTO.get());

		return Response.ok(serviceDescriptionDTO).build();
	}

	@GET
	@Path("by-name")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllServiceDescriptions(@QueryParam("serviceDescriptionName") final String serviceDescriptionName)
			throws BusinessException {

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionQueryService
				.fetchServiceDescriptionByName(serviceDescriptionName);
		final Long totalServiceDescriptions = this.serviceDescriptionQueryService.countServiceDescriptions();

		return Response.ok(new ServiceDescriptionsDTO(serviceDescriptions, totalServiceDescriptions)).build();
	}
}
