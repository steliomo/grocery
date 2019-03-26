/**
 *
 */
package mz.co.grocery.integ.resources.user.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final UserContext userContext;

	public UserDetailsImpl(final UserContext userContext) {
		this.userContext = userContext;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.userContext.getPassword();
	}

	@Override
	public String getUsername() {
		return this.userContext.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getUuid() {
		return this.userContext.getUuid();
	}
}
