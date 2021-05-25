/**
 *
 */
package mz.co.grocery.integ.resources.grocery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UnitDetail;
import mz.co.grocery.core.grocery.model.UserRole;
import mz.co.grocery.core.grocery.service.GroceryQueryService;
import mz.co.grocery.core.grocery.service.GroceryUserQueryService;
import mz.co.grocery.core.grocery.service.GroceryUserService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.grocery.dto.GroceryUserDTO;
import mz.co.grocery.integ.resources.mail.Mail;
import mz.co.grocery.integ.resources.mail.MailSenderService;
import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.dto.UsersDTO;
import mz.co.grocery.integ.resources.user.service.ResourceOnwnerService;
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
	private Mail mail;

	@Inject
	private MailSenderService mailSenderService;

	@Inject
	private GroceryQueryService groceryQueryService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registGroceryUser(final UserDTO userDTO) throws BusinessException {

		final String userUuid = this.resourceOnwnerService.createUser(this.getContext(), userDTO);
		final GroceryUser groceryUser = userDTO.getGroceryUserDTO().get();

		groceryUser.setUser(userUuid);

		this.groceryUserService.createGroceryUser(this.getContext(), groceryUser);
		return Response.ok(userDTO).build();
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

		final List<UserDTO> userDTOs = groceryUsers.stream().map(groceryUser -> {

			final UserDTO userDTO = client.target(UrlTargets.ACCOUNT_MODULE)
					.path("users/by-uuid/" + groceryUser.getUser()).request(MediaType.APPLICATION_JSON)
					.get(UserDTO.class);

			userDTO.setGroceryUserDTO(new GroceryUserDTO(groceryUser));

			return userDTO;

		}).collect(Collectors.toList());

		return Response.ok(new UsersDTO(userDTOs, totalItems)).build();
	}

	@POST
	@Path("add-saler")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSaler(final UserDTO userDTO) throws BusinessException {

		final String userUuid = this.resourceOnwnerService.createUser(this.getContext(), userDTO);
		final GroceryUser groceryUser = userDTO.getGroceryUserDTO().get();

		groceryUser.setUser(userUuid);
		groceryUser.setUserRole(UserRole.OPERATOR);
		groceryUser.setExpiryDate(LocalDate.now().plusMonths(3));

		this.groceryUserService.createGroceryUser(this.getContext(), groceryUser);

		this.mail.setMailTemplate("signup-template.txt");
		this.mail.setMailTo(userDTO.getEmail());
		this.mail.setMailSubject(this.mail.getWelcome());

		final Grocery grocery = this.groceryQueryService.findByUuid(groceryUser.getGrocery().getUuid());

		this.mail.setParam("fullName", userDTO.getFullName());
		this.mail.setParam("username", userDTO.getUsername());
		this.mail.setParam("password", userDTO.getPassword());
		this.mail.setParam("email", userDTO.getEmail());
		this.mail.setParam("grocery", grocery.getName());
		this.mail.setParam("address", grocery.getAddress());

		this.mailSenderService.send(this.mail);

		return Response.ok(userDTO).build();
	}

	@GET
	@Path("unit-detail/{unitUuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUntDetails(@PathParam("unitUuid") final String unitUuid) throws BusinessException {

		final UnitDetail unitDetail = this.groceryUserQueryService.findUnitDetailsByUuid(unitUuid);

		return Response.ok(unitDetail).build();
	}
}
