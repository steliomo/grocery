/**
 *
 */
package mz.co.grocery.integ.resources.user.service;

import static mz.co.grocery.integ.resources.user.service.ResourceOwnerServiceImpl.NAME;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.grocery.GroceryUserDTO;
import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.model.ResourceOwner;
import mz.co.grocery.integ.resources.util.UrlTargets;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@Service(NAME)
public class ResourceOwnerServiceImpl implements UserDetailsService, ResourceOnwnerService {

	public static final String NAME = "mz.co.grocery.integ.resources.user.service.ResourceOwnerServiceImpl";

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final UserContext user = new UserContext();
		user.setUsername(username);

		final Client client = ClientBuilder.newClient();
		final UserDTO get = client.target(UrlTargets.ACCOUNT_MODULE).path("users/user/" + username)
		        .request(MediaType.APPLICATION_JSON).get(UserDTO.class);

		user.setPassword(get.getPassword());
		user.setUuid(get.getUuid());
		user.setFullName(get.getFullName());

		return new ResourceOwner(user);
	}

	@Override
	public String createUser(final UserContext userContext, final GroceryUserDTO groceryUserDTO) {
		final Client client = ClientBuilder.newClient();

		final UserContext context = new UserContext();
		context.setUuid(userContext.getUuid());
		context.setFullName(groceryUserDTO.getFullName());
		context.setUsername(groceryUserDTO.getUsername());
		context.setPassword(groceryUserDTO.getPassword());

		final UserContext post = client.target(UrlTargets.ACCOUNT_MODULE).path("users/create")
		        .request(MediaType.APPLICATION_JSON)
		        .post(Entity.entity(context, MediaType.APPLICATION_JSON), UserContext.class);

		return post.getUuid();
	}
}
