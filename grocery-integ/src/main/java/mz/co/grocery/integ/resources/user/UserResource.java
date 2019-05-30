/**
 *
 */
package mz.co.grocery.integ.resources.user;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.AbstractResource;
import mz.co.grocery.integ.resources.user.model.ResourceOwner;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

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

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(final UserContext context) throws BusinessException {

		final Authentication authenticate = this.authenticationProvider
		        .authenticate(new UsernamePasswordAuthenticationToken(context.getUsername(), context.getPassword()));

		if (!authenticate.isAuthenticated()) {
			throw new BusinessException("Invalid credentials");
		}

		final ResourceOwner resourceOwner = (ResourceOwner) authenticate.getPrincipal();
		final UserContext userContext = resourceOwner.getUserContext();

		userContext.setPassword(null);

		return Response.ok(userContext).build();
	}
}
