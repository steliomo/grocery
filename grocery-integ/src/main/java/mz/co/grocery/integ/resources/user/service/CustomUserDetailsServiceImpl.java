/**
 *
 */
package mz.co.grocery.integ.resources.user.service;

import static mz.co.grocery.integ.resources.user.service.CustomUserDetailsServiceImpl.NAME;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mz.co.grocery.integ.resources.user.dto.UserDTO;
import mz.co.grocery.integ.resources.user.model.UserDetailsImpl;
import mz.co.grocery.integ.resources.util.UrlTargets;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@Service(NAME)
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	public static final String NAME = "mz.co.msaude.consultation.integ.resources.config.user.service.CustomUserDetailsServiceImpl";

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final UserContext user = new UserContext();
		user.setUsername(username);

		final Client client = ClientBuilder.newClient();
		final UserDTO get = client.target(UrlTargets.ACCOUNT_MODULE).path("users/user/" + username)
		        .request(MediaType.APPLICATION_JSON).get(UserDTO.class);

		user.setPassword(get.getPassword());
		user.setUuid(get.getCreatedBy());

		return new UserDetailsImpl(user);
	}
}
