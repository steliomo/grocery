/**
 *
 */
package mz.co.grocery.integ.resources.unit.dto;

import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.core.domain.unit.UserRole;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class UnitUserDTO extends GenericDTO {

	private UnitDTO unitDTO;

	private String user;

	private UserRole userRole;

	private LocalDate expiryDate;

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
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
