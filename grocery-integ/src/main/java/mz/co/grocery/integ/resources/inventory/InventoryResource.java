/**
 *
 */
package mz.co.grocery.integ.resources.inventory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.inventory.service.InventoryQueryService;
import mz.co.grocery.core.inventory.service.InventoryService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.inventory.dto.InventoryDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("inventories")
@Service(InventoryResource.NAME)
public class InventoryResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.inventory.InventoryResource";

	@Inject
	private InventoryQueryService inventoryQueryService;

	@Inject
	private InventoryService inventoryService;

	@GET
	@Path("by-grocery-and-status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInventoryByGroceryAndStatus(@QueryParam("groceryUuid") final String groceryUuid,
	        @QueryParam("inventoryStatus") final InventoryStatus inventoryStatus) throws BusinessException {

		final Grocery grocery = new Grocery();
		grocery.setUuid(groceryUuid);

		final Inventory inventory = this.inventoryQueryService.fetchInventoryByGroceryAndStatus(grocery,
		        inventoryStatus);

		return Response.ok(new InventoryDTO(inventory)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response performInventory(final Inventory inventory) throws BusinessException {
		this.inventoryService.performInventory(this.getContext(), inventory);
		return Response.ok(new InventoryDTO(inventory)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response aproveInventory(final Inventory inventory) throws BusinessException {
		this.inventoryService.approveInventory(this.getContext(), inventory);
		return Response.ok(new InventoryDTO(inventory)).build();
	}
}
