/**
 *
 */
package mz.co.grocery.integ.resources.user.dto;

import java.util.Collections;
import java.util.List;

/**
 * @author Stélio Moiane
 *
 */
public class UsersDTO {

	private final List<UserDTO> usersDTO;

	private final Long totalItems;

	public UsersDTO(final List<UserDTO> userDTOs, final Long totalItems) {
		this.usersDTO = userDTOs;
		this.totalItems = totalItems;
	}

	public List<UserDTO> getUsersDTO() {
		return Collections.unmodifiableList(this.usersDTO);
	}

	public Long getTotalItems() {
		return this.totalItems;
	}
}
