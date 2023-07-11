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

import mz.co.grocery.core.application.item.out.ServicePort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.item.dto.ServiceDTO;
import mz.co.grocery.integ.resources.item.dto.ServicesDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("services")
@WebAdapter
public class ServiceResource extends AbstractResource {

	@Inject
	private ServicePort servicePort;

	@Autowired
	private DTOMapper<ServiceDTO, Service> serviceMapper;

	@Autowired
	private DTOMapper<UnitDTO, Unit> unitMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createService(final ServiceDTO serviceDTO) throws BusinessException {

		Service service = this.serviceMapper.toDomain(serviceDTO);

		service = this.servicePort.createService(this.getContext(), service);

		return Response.ok(this.serviceMapper.toDTO(service)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findServices(@QueryParam("currentPage") final int currentPage, @QueryParam("maxResult") final int maxResult)
			throws BusinessException {

		final List<Service> services = this.servicePort.findAllServices(currentPage, maxResult);

		final Long totalItems = this.servicePort.countServices();

		final ServicesDTO servicesDTO = new ServicesDTO(services, totalItems, this.serviceMapper);

		return Response.ok(servicesDTO).build();
	}

	@GET
	@Path("{serviceUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findServiceByUuid(@PathParam("serviceUuid") final String serviceUuid) throws BusinessException {

		final Service service = this.servicePort.findServiceByUuid(serviceUuid);

		return Response.ok(this.serviceMapper.toDTO(service)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateService(final ServiceDTO serviceDTO) throws BusinessException {

		Service service = this.serviceMapper.toDomain(serviceDTO);

		service = this.servicePort.updateService(this.getContext(), service);

		return Response.ok(this.serviceMapper.toDTO(service)).build();
	}

	@GET
	@Path("by-name/{serviceName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findServiceByName(@PathParam("serviceName") final String serviceName) throws BusinessException {
		final List<Service> services = this.servicePort.findServicesByName(serviceName);

		return Response.ok(new ServicesDTO(services, 0L, this.serviceMapper)).build();
	}

	@GET
	@Path("by-unit/{unitUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findServiceByUnitUuid(@PathParam("unitUuid") final String unitUuid) throws BusinessException {
		final UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUuid(unitUuid);

		final List<Service> services = this.servicePort.findServicesByUnit(this.unitMapper.toDomain(unitDTO));

		return Response.ok(new ServicesDTO(services, 0L, this.serviceMapper).getServicesDTO()).build();
	}

	@GET
	@Path("not-in-this-unit/{unitUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findServiceNotInThisUnit(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUuid(unitUuid);

		final List<Service> services = this.servicePort.findServicesNotInthisUnit(this.unitMapper.toDomain(unitDTO));

		return Response.ok(new ServicesDTO(services, 0L, this.serviceMapper).getServicesDTO()).build();
	}
}
