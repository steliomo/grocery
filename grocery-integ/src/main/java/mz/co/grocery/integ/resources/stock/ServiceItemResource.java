/**
 *
 */
package mz.co.grocery.integ.resources.stock;

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

import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.grocery.core.stock.service.ServiceItemQueryService;
import mz.co.grocery.core.stock.service.ServiceItemService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.grocery.integ.resources.product.dto.ServiceDTO;
import mz.co.grocery.integ.resources.stock.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.stock.dto.ServiceItemsDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@Path("service-items")
@Service(ServiceItemResource.NAME)
public class ServiceItemResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.stock.ServiceItemResource";

	@Inject
	private ServiceItemService serviceItemService;

	@Inject
	private ServiceItemQueryService serviceItemQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createServiceItem(final ServiceItemDTO serviceItemDTO) throws BusinessException {
		this.serviceItemService.createServiceItem(this.getContext(), serviceItemDTO.get());
		return Response.ok(serviceItemDTO).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllServiceItems(@QueryParam("currentPage") final int currentPage, @QueryParam("maxResult") final int maxResult)
			throws BusinessException {

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchAllServiceItems(currentPage, maxResult);
		final Long totalItems = this.serviceItemQueryService.countServiceItems();

		return Response.ok(new ServiceItemsDTO(serviceItems, totalItems)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateServiceItem(final ServiceItemDTO serviceItemDTO) throws BusinessException {
		this.serviceItemService.updateServiceItem(this.getContext(), serviceItemDTO.get());
		return Response.ok(serviceItemDTO).build();
	}

	@GET
	@Path("{serviceItemUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemByUuid(@PathParam("serviceItemUuid") final String serviceItemUuid) throws BusinessException {
		final ServiceItem serviceItem = this.serviceItemQueryService.fetchServiceItemByUuid(serviceItemUuid);

		return Response.ok(new ServiceItemDTO(serviceItem)).build();
	}

	@GET
	@Path("by-name")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemsByName(@QueryParam("serviceItemName") final String serviceItemName)
			throws BusinessException {

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchServiceItemByName(serviceItemName);
		final Long totalItems = this.serviceItemQueryService.countServiceItems();

		return Response.ok(new ServiceItemsDTO(serviceItems, totalItems)).build();
	}

	@GET
	@Path("by-service-and-unit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemsByServiceAndUnit(@QueryParam("serviceUuid") final String serviceUuid,
			@QueryParam("unitUuid") final String unitUuid)
					throws BusinessException {

		final ServiceDTO serviceDTO = new ServiceDTO(serviceUuid);
		final GroceryDTO unitDTO = new GroceryDTO(unitUuid);

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchServiceItemsByServiceAndUnit(serviceDTO.get(), unitDTO.get());

		return Response.ok(new ServiceItemsDTO(serviceItems, 0L).getServiceItemsDTO()).build();
	}

	@GET
	@Path("not-in-this-unit-by-service")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchServiceItemsNotInThisUnitByService(@QueryParam("serviceUuid") final String serviceUuid,
			@QueryParam("unitUuid") final String unitUuid)
					throws BusinessException {

		final ServiceDTO serviceDTO = new ServiceDTO(serviceUuid);
		final GroceryDTO unitDTO = new GroceryDTO(unitUuid);

		final List<ServiceItem> serviceItems = this.serviceItemQueryService.fetchServiceItemsNotInThisUnitByService(serviceDTO.get(), unitDTO.get());

		return Response.ok(new ServiceItemsDTO(serviceItems, 0L).getServiceItemsDTO()).build();
	}
}
