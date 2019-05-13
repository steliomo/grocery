/**
 *
 */
package mz.co.grocery.integ.resources.user;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.AbstractResource;

/**
 * @author Stélio Moiane
 *
 */

@Path("users")
@Service(UserResource.NAME)
public class UserResource extends AbstractResource {

	public static final String NAME = "mz.co.grocery.integ.resources.user.UserResource";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response login() {
		return Response.ok(this.getContext()).build();
	}
}
