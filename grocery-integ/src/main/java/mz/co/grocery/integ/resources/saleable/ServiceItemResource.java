/**
 *
 */
package mz.co.grocery.integ.resources.saleable;

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

import mz.co.grocery.core.application.sale.out.ServiceItemPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.item.dto.ServiceDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemsDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Path("service-items")
@WebAdapter
public class ServiceItemResource extends AbstractResource {

	@Inject
	private ServiceItemPort serviceItemPort;

	@Autowired
	private DTOMapper<ServiceItemDTO, ServiceItem> serviceItemMapper;

	@Autowired
	private DTOMapper<UnitDTO, Unit> unitMapper;

	@Autowired
	private DTOMapper<ServiceDTO, Service> serviceMapper;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceItem(final ServiceItemDTO serviceItemDTO) throws BusinessException {

		ServiceItem serviceItem = this.serviceItemMapper.toDomain(serviceItemDTO);

		serviceItem = this.serviceItemPort.createServiceItem(this.getContext(), serviceItem);

		return Response.ok(serviceItemDTO).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllServiceItems(@QueryParam("currentPage") final int currentPage, @QueryParam("maxResult") final int maxResult)
			throws BusinessException {

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchAllServiceItems(currentPage, maxResult);

		final Long totalItems = this.serviceItemPort.countServiceItems();

		return Response.ok(new ServiceItemsDTO(serviceItems, totalItems, this.serviceItemMapper)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateServiceItem(final ServiceItemDTO serviceItemDTO) throws BusinessException {
		ServiceItem serviceItem = this.serviceItemMapper.toDomain(serviceItemDTO);

		serviceItem = this.serviceItemPort.updateServiceItem(this.getContext(), serviceItem);

		return Response.ok(this.serviceItemMapper.toDTO(serviceItem)).build();
	}

	@GET
	@Path("{serviceItemUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemByUuid(@PathParam("serviceItemUuid") final String serviceItemUuid) throws BusinessException {
		final ServiceItem serviceItem = this.serviceItemPort.fetchServiceItemByUuid(serviceItemUuid);

		return Response.ok(this.serviceItemMapper.toDTO(serviceItem)).build();
	}

	@GET
	@Path("by-name")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemsByName(@QueryParam("serviceItemName") final String serviceItemName)
			throws BusinessException {

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchServiceItemByName(serviceItemName);

		final Long totalItems = this.serviceItemPort.countServiceItems();

		return Response.ok(new ServiceItemsDTO(serviceItems, totalItems, this.serviceItemMapper)).build();
	}

	@GET
	@Path("by-service-and-unit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemsByServiceAndUnit(@QueryParam("serviceUuid") final String serviceUuid,
			@QueryParam("unitUuid") final String unitUuid)
					throws BusinessException {

		final ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.setUuid(serviceUuid);

		final UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUuid(unitUuid);

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchServiceItemsByServiceAndUnit(this.serviceMapper.toDomain(serviceDTO),
				this.unitMapper.toDomain(unitDTO));

		return Response.ok(new ServiceItemsDTO(serviceItems, 0L, this.serviceItemMapper).getServiceItemsDTO()).build();
	}

	@GET
	@Path("not-in-this-unit-by-service")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemsNotInThisUnitByService(@QueryParam("serviceUuid") final String serviceUuid,
			@QueryParam("unitUuid") final String unitUuid)
					throws BusinessException {

		final ServiceDTO serviceDTO = new ServiceDTO();
		serviceDTO.setUuid(serviceUuid);

		final UnitDTO unitDTO = new UnitDTO();
		unitDTO.setUuid(unitUuid);

		final List<ServiceItem> serviceItems = this.serviceItemPort.fetchServiceItemsNotInThisUnitByService(this.serviceMapper.toDomain(serviceDTO),
				this.unitMapper.toDomain(unitDTO));

		return Response.ok(new ServiceItemsDTO(serviceItems, 0L, this.serviceItemMapper).getServiceItemsDTO()).build();
	}
}
