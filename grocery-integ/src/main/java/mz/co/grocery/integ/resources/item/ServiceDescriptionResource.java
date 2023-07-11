/**
 *
 */
package mz.co.grocery.integ.resources.item;

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

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.item.out.ServiceDescriptionPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.item.dto.ServiceDescriptionDTO;
import mz.co.grocery.integ.resources.item.dto.ServiceDescriptionsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("service-descriptions")
@WebAdapter
public class ServiceDescriptionResource extends AbstractResource {

	@Inject
	private ServiceDescriptionPort serviceDescriptionPort;

	@Autowired
	private DTOMapper<ServiceDescriptionDTO, ServiceDescription> serviceDescriptionMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceDescription(final ServiceDescriptionDTO serviceDescriptionDTO) throws BusinessException {

		ServiceDescription serviceDescription = this.serviceDescriptionMapper.toDomain(serviceDescriptionDTO);

		serviceDescription = this.serviceDescriptionPort.createServiceDescription(this.getContext(), serviceDescription);

		return Response.ok(this.serviceDescriptionMapper.toDTO(serviceDescription)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllServiceDescriptions(@QueryParam("currentPage") final int currentPage, @QueryParam("maxResult") final int maxResult)
			throws BusinessException {

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionPort.findAllServiceDescriptions(currentPage, maxResult);

		final Long totalServiceDescriptions = this.serviceDescriptionPort.countServiceDescriptions();

		return Response.ok(new ServiceDescriptionsDTO(serviceDescriptions, totalServiceDescriptions, this.serviceDescriptionMapper)).build();
	}

	@GET
	@Path("{serviceDescriptionUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceDescriptionByUuid(@PathParam("serviceDescriptionUuid") final String serviceDescriptionUuid)
			throws BusinessException {

		final ServiceDescription serviceDescription = this.serviceDescriptionPort.fetchServiceDescriptionByUuid(serviceDescriptionUuid);

		return Response.ok(this.serviceDescriptionMapper.toDTO(serviceDescription)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateServiceDescription(final ServiceDescriptionDTO serviceDescriptionDTO) throws BusinessException {

		ServiceDescription serviceDescription = this.serviceDescriptionMapper.toDomain(serviceDescriptionDTO);

		serviceDescription = this.serviceDescriptionPort.updateServiceDescription(this.getContext(), serviceDescription);

		return Response.ok(this.serviceDescriptionMapper.toDTO(serviceDescription)).build();
	}

	@GET
	@Path("by-name")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllServiceDescriptions(@QueryParam("serviceDescriptionName") final String serviceDescriptionName)
			throws BusinessException {

		final List<ServiceDescription> serviceDescriptions = this.serviceDescriptionPort
				.fetchServiceDescriptionByName(serviceDescriptionName);

		final Long totalServiceDescriptions = this.serviceDescriptionPort.countServiceDescriptions();

		return Response.ok(new ServiceDescriptionsDTO(serviceDescriptions, totalServiceDescriptions, this.serviceDescriptionMapper)).build();
	}
}
