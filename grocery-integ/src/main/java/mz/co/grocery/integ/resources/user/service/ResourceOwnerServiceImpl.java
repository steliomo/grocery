/**
 *
 */
package mz.co.grocery.integ.resources.user.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.model.ResourceOwner;
import mz.co.grocery.integ.resources.util.UrlTargets;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@Service(ResourceOwnerServiceImpl.NAME)
public class ResourceOwnerServiceImpl implements UserDetailsService, ResourceOnwnerService {

	public static final String NAME = "mz.co.grocery.integ.resources.user.service.ResourceOwnerServiceImpl";

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final UserContext user = new UserContext();
		user.setUsername(username);

		final Client client = ClientBuilder.newClient();

		final Response response = client.target(UrlTargets.ACCOUNT_MODULE).path("users/user/" + username)
				.request(MediaType.APPLICATION_JSON).get();

		if (response.getStatusInfo() == Response.Status.NOT_FOUND) {
			throw new UsernameNotFoundException("Username does not exsit");
		}

		final UserDTO userDTO = response.readEntity(UserDTO.class);

		user.setPassword(userDTO.getPassword());
		user.setUuid(userDTO.getUuid());
		user.setFullName(userDTO.getFullName());

		return new ResourceOwner(user);
	}

	@Override
	public String createUser(final UserContext userContext, final UserDTO userDTO) {
		final Client client = ClientBuilder.newClient();

		final UserContext context = new UserContext();
		context.setUuid(userContext.getUuid());
		context.setFullName(userDTO.getFullName());
		context.setUsername(userDTO.getUsername());
		context.setPassword(userDTO.getPassword());
		context.setEmail(userDTO.getEmail());

		final UserContext post = client.target(UrlTargets.ACCOUNT_MODULE).path("users/create")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(context, MediaType.APPLICATION_JSON), UserContext.class);

		return post.getUuid();
	}
}
