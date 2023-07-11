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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import mz.co.grocery.core.application.inventory.in.ApproveInventoryUseCase;
import mz.co.grocery.core.application.inventory.in.PerformInventoroyUseCase;
import mz.co.grocery.core.application.inventory.out.InventoryPort;
import mz.co.grocery.core.common.WebAdapter;
import mz.co.grocery.core.domain.inventory.Inventory;
import mz.co.grocery.core.domain.inventory.InventoryStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.inventory.dto.InventoryDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */
@Path("inventories")
@WebAdapter
public class InventoryResource extends AbstractResource {

	@Inject
	private InventoryPort inventoryPort;

	@Inject
	private PerformInventoroyUseCase performInventoroyUseCase;

	@Inject
	private ApproveInventoryUseCase ApproveInventoryUseCase;

	@Autowired
	private DTOMapper<InventoryDTO, Inventory> inventoryMapper;

	@GET
	@Path("by-grocery-and-status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInventoryByGroceryAndStatus(@QueryParam("groceryUuid") final String groceryUuid,
			@QueryParam("inventoryStatus") final InventoryStatus inventoryStatus) throws BusinessException {

		final Unit grocery = new Unit();
		grocery.setUuid(groceryUuid);

		final Inventory inventory = this.inventoryPort.fetchInventoryByGroceryAndStatus(grocery,
				inventoryStatus);

		return Response.ok(this.inventoryMapper.toDTO(inventory)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response performInventory(final InventoryDTO inventoryDTO) throws BusinessException {

		Inventory inventory = this.inventoryMapper.toDomain(inventoryDTO);

		inventory = this.performInventoroyUseCase.performInventory(this.getContext(), inventory);

		return Response.ok(this.inventoryMapper.toDTO(inventory)).build();
	}

	@PUT
	@Path("{inventoryUuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response aproveInventory(@PathParam("inventoryUuid") final String inventoryUuid) throws BusinessException {

		final Inventory inventory = this.ApproveInventoryUseCase.approveInventory(this.getContext(), inventoryUuid);

		return Response.ok(this.inventoryMapper.toDTO(inventory)).build();
	}
}
