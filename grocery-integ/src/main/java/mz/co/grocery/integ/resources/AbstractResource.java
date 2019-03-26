/**
 *
 */
package mz.co.grocery.integ.resources;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import mz.co.grocery.integ.resources.user.model.UserDetailsImpl;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public abstract class AbstractResource {

	private final UserContext context;

	public AbstractResource() {
		this.context = new UserContext();
	}

	public UserContext getContext() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
		this.context.setUuid(principal.getUuid());
		return this.context;
	}
}
