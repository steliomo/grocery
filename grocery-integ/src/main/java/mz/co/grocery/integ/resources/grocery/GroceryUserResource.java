/**
 *
 */
package mz.co.grocery.integ.resources.grocery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UserRole;
import mz.co.grocery.core.grocery.service.GroceryUserQueryService;
import mz.co.grocery.core.grocery.service.GroceryUserService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.service.ResourceOnwnerService;
import mz.co.grocery.integ.resources.util.SmsResource;
import mz.co.grocery.integ.resources.util.UrlTargets;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
@Service(GroceryUserResource.NAME)
@Path("grocery-users")
public class GroceryUserResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.grocery.GroceryUserResource";

	@Inject
	private ResourceOnwnerService resourceOnwnerService;

	@Inject
	private GroceryUserService groceryUserService;

	@Inject
	private GroceryUserQueryService groceryUserQueryService;

	@Inject
	private SmsResource smsResource;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registGroceryUser(final GroceryUserDTO groceryUserDTO) throws BusinessException {

		final String userUuid = this.resourceOnwnerService.createUser(this.getContext(), groceryUserDTO);
		final GroceryUser groceryUser = groceryUserDTO.getGroceryUser();

		groceryUser.setUser(userUuid);

		final StringBuilder text = new StringBuilder();
		text.append("O(A) Sr(a). ").append(groceryUserDTO.getFullName())
		        .append(" foi registado com sucesso como utilizador(a) no aplicativo MERCEARIAS.")
		        .append(" O seu utilizador é: ").append(groceryUserDTO.getUsername())
		        .append(". A senha administrativa é: ").append(groceryUserDTO.getPassword()).append(". Obrigado");

		this.smsResource.send(groceryUserDTO.getUsername(), text.toString());

		this.groceryUserService.createGroceryUser(this.getContext(), groceryUser);
		return Response.ok(groceryUserDTO).build();
	}

	@GET
	@Path("user-roles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserRoles() throws BusinessException {
		return Response.ok(UserRole.values()).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findGroceryUsers(@QueryParam("currentPage") final int currentPage,
	        @QueryParam("maxResult") final int maxResult) throws BusinessException {

		final Long totalItems = this.groceryUserQueryService.count();

		if (totalItems == 0L) {
			return Response.ok(new ArrayList<>()).build();
		}

		final List<GroceryUser> groceryUsers = this.groceryUserQueryService.fetchAllGroceryUsers(currentPage,
		        maxResult);

		final Client client = ClientBuilder.newClient();

		final List<GroceryUserDTO> groceryUserDTOs = groceryUsers.stream().map(groceryUser -> {

			final UserDTO userDTO = client.target(UrlTargets.ACCOUNT_MODULE)
			        .path("users/by-uuid/" + groceryUser.getUser()).request(MediaType.APPLICATION_JSON)
			        .get(UserDTO.class);

			return new GroceryUserDTO(groceryUser, userDTO, totalItems);

		}).collect(Collectors.toList());

		return Response.ok(groceryUserDTOs).build();
	}
}
