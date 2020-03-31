/**
 *
 */
package mz.co.grocery.integ.resources.grocery;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.service.GroceryQueryService;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.grocery.dto.GroceriesDTO;
import mz.co.grocery.integ.resources.grocery.dto.GroceryDTO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Path("groceries")
@Service(GroceryResource.NAME)
public class GroceryResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.grocery.GroceryResource";

	@Inject
	private GroceryService groceryService;

	@Inject
	private GroceryQueryService groceryQueryService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createGrocery(final Grocery grocery) throws BusinessException {
		this.groceryService.createGrocery(this.getContext(), grocery);
		return Response.ok(grocery).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findGroceries(@QueryParam("currentPage") final int currentPage,
	        @QueryParam("maxResult") final int maxResult) throws BusinessException {

		final Long totalItems = this.groceryQueryService.count();
		final List<Grocery> groceries = this.groceryQueryService.findAllGroceries(currentPage, maxResult);

		final List<GroceryDTO> groceriesDTO = groceries.stream().map(grocery -> new GroceryDTO(grocery))
		        .collect(Collectors.toList());

		return Response.ok(new GroceriesDTO(groceriesDTO, totalItems)).build();
	}
}
