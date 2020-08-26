/**
 *
 */
package mz.co.grocery.integ.resources.user;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UserRole;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.core.grocery.service.GroceryUserQueryService;
import mz.co.grocery.core.grocery.service.GroceryUserService;
import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.grocery.dto.GroceryUserDTO;
import mz.co.grocery.integ.resources.mail.Mail;
import mz.co.grocery.integ.resources.mail.MailSenderService;
import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.model.ResourceOwner;
import mz.co.grocery.integ.resources.user.service.ResourceOnwnerService;
import mz.co.grocery.integ.resources.util.UrlTargets;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.util.RandomUtil;
import mz.co.msaude.boot.frameworks.util.UuidFactory;

/**
 * @author Stélio Moiane
 *
 */

@Path("users")
@Service(UserResource.NAME)
public class UserResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.user.UserResource";

	@Inject
	private AuthenticationProvider authenticationProvider;

	@Inject
	private GroceryUserQueryService groceryUserQueryService;

	@Inject
	private ResourceOnwnerService resourceOnwnerService;

	@Inject
	private GroceryUserService groceryUserService;

	@Inject
	private GroceryService groceryService;

	@Inject
	private MailSenderService mailSenderService;

	@Inject
	private Mail mail;

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(final UserContext context) throws BusinessException {

		Authentication authenticate = null;

		try {
			authenticate = this.authenticationProvider.authenticate(
					new UsernamePasswordAuthenticationToken(context.getUsername(), context.getPassword()));
		} catch (final BadCredentialsException e) {
			throw new BusinessException("Invalid credentials");
		}

		if (!authenticate.isAuthenticated()) {
			throw new BusinessException("Invalid credentials");
		}

		final ResourceOwner resourceOwner = (ResourceOwner) authenticate.getPrincipal();
		final UserContext userContext = resourceOwner.getUserContext();

		final UserDTO userDTO = new UserDTO();
		userDTO.setUuid(userContext.getUuid());
		userDTO.setFullName(userContext.getFullName());
		userDTO.setEmail(context.getEmail());

		final GroceryUser groceryUser = this.groceryUserQueryService.fetchGroceryUserByUser(userDTO.getUuid());
		userDTO.setGroceryUserDTO(new GroceryUserDTO(groceryUser));

		return Response.ok(userDTO).build();
	}

	@PUT
	@Path("reset-password")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response resetPassword(final UserDTO user) throws BusinessException {
		final int PASSWORD_LENGTH = 4;

		final Client client = ClientBuilder.newClient();

		final Response response = client.target(UrlTargets.ACCOUNT_MODULE).path("users/user/" + user.getUsername())
				.request(MediaType.APPLICATION_JSON).get();

		if (response.getStatusInfo() == Response.Status.NOT_FOUND) {
			throw new BusinessException("User not found");
		}

		final UserDTO userDTO = response.readEntity(UserDTO.class);

		userDTO.setPassword(RandomUtil.password(PASSWORD_LENGTH));
		userDTO.setProperties("reset", Boolean.TRUE);

		client.target(UrlTargets.ACCOUNT_MODULE).path("users/update")
		.request(MediaType.APPLICATION_JSON)
		.put(Entity.entity(userDTO, MediaType.APPLICATION_JSON), UserDTO.class);

		this.sendResetPasswordEmail(userDTO);

		userDTO.setPassword(null);

		return Response.ok(userDTO).build();
	}

	private void sendResetPasswordEmail(final UserDTO userDTO) throws BusinessException {
		this.mail.setMailTemplate("reset-password-template.txt");
		this.mail.setMailTo(userDTO.getEmail());
		this.mail.setMailSubject(this.mail.getResetPassword());

		this.mail.setParam("fullName", userDTO.getFullName());
		this.mail.setParam("username", userDTO.getUsername());
		this.mail.setParam("password", userDTO.getPassword());

		this.mailSenderService.send(this.mail);
	}

	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response sigUp(final UserDTO user) throws BusinessException {

		final Client client = ClientBuilder.newClient();

		final Response response = client.target(UrlTargets.ACCOUNT_MODULE).path("users/user/" + user.getUsername())
				.request(MediaType.APPLICATION_JSON).get();

		if (response.getStatusInfo() == Response.Status.OK) {
			throw new BusinessException("This username already exist in the system. Choose another");
		}

		final UserContext context = new UserContext();
		context.setUuid(UuidFactory.generate());

		final String userId = this.resourceOnwnerService.createUser(context, user);

		final GroceryUser groceryUser = user.getGroceryUserDTO().get();
		groceryUser.setUserRole(UserRole.MANAGER);
		groceryUser.setExpiryDate(LocalDate.now().plusMonths(3));
		groceryUser.setUser(userId);

		this.groceryService.createGrocery(context, groceryUser.getGrocery());
		this.groceryUserService.createGroceryUser(context, groceryUser);

		this.sendSignUpEmail(user, groceryUser);

		user.setPassword(null);

		return Response.ok(user).build();
	}

	private void sendSignUpEmail(final UserDTO user, final GroceryUser groceryUser) throws BusinessException {
		this.mail.setMailTemplate("signup-template.txt");
		this.mail.setMailTo(user.getEmail());
		this.mail.setMailSubject(this.mail.getWelcome());

		this.mail.setParam("fullName", user.getFullName());
		this.mail.setParam("username", user.getUsername());
		this.mail.setParam("password", user.getPassword());
		this.mail.setParam("email", user.getEmail());
		this.mail.setParam("grocery", groceryUser.getGrocery().getName());
		this.mail.setParam("address", groceryUser.getGrocery().getAddress());

		this.mailSenderService.send(this.mail);
	}
}
