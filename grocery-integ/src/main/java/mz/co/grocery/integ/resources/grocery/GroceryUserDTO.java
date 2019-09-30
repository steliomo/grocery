/**
 *
 */
package mz.co.grocery.integ.resources.grocery;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UserRole;
import mz.co.grocery.integ.resources.user.dto.UserDTO;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryUserDTO {

	private String fullName;

	private Grocery grocery;

	private String username;

	private String password;

	private UserRole userRole;

	private LocalDate expiryDate;

	private String email;

	private Long totalItems;

	public GroceryUserDTO() {
	}

	public GroceryUserDTO(final GroceryUser groceryUser, final UserDTO userDTO, final Long totalItems) {
		this.fullName = userDTO.getFullName();
		this.grocery = groceryUser.getGrocery();
		this.username = userDTO.getUsername();
		this.userRole = groceryUser.getUserRole();
		this.expiryDate = groceryUser.getExpiryDate();
		this.totalItems = totalItems;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public Grocery getGrocery() {
		return this.grocery;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public LocalDate getExpiryDate() {
		return this.expiryDate;
	}

	public Long getTotalItems() {
		return this.totalItems;
	}

	@JsonIgnore
	public GroceryUser getGroceryUser() {

		final GroceryUser groceryUser = new GroceryUser();
		groceryUser.setGrocery(this.grocery);
		groceryUser.setUserRole(this.userRole);
		groceryUser.setExpiryDate(this.expiryDate);

		return groceryUser;
	}
}
