/**
 *
 */
package mz.co.grocery.core.domain.unit;

import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */
public class UnitUser extends Domain {

	private Unit unit;

	private String user;

	private UserRole userRole;

	private LocalDate expiryDate;

	public Optional<Unit> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final Unit unit) {
		this.unit = unit;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(final UserRole userRole) {
		this.userRole = userRole;
	}

	public LocalDate getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(final LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
}
