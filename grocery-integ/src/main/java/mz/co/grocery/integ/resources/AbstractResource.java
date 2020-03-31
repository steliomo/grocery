/**
 *
 */
package mz.co.grocery.integ.resources;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import mz.co.grocery.integ.resources.user.model.ResourceOwner;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public abstract class AbstractResource {

	private UserContext context;

	public AbstractResource() {
		this.context = new UserContext();
	}

	public UserContext getContext() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final ResourceOwner principal = (ResourceOwner) authentication.getPrincipal();
		this.context = principal.getUserContext();
		this.context.setPassword(null);
		return this.context;
	}
}
