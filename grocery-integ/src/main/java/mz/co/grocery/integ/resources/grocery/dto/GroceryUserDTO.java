/**
 *
 */
package mz.co.grocery.integ.resources.grocery.dto;

import java.time.LocalDate;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UserRole;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryUserDTO extends GenericDTO<GroceryUser> {

	private GroceryDTO groceryDTO;

	private String user;

	private UserRole userRole;

	private LocalDate expiryDate;

	public GroceryUserDTO() {

	}

	public GroceryUserDTO(final GroceryUser groceryUser) {
		super(groceryUser);
	}

	@Override
	public void mapper(final GroceryUser groceryUser) {
		this.groceryDTO = new GroceryDTO(groceryUser.getGrocery());
		this.user = groceryUser.getUser();
		this.userRole = groceryUser.getUserRole();
		this.expiryDate = groceryUser.getExpiryDate();
	}

	@Override
	public GroceryUser get() {
		final GroceryUser groceryUser = this.get(new GroceryUser());
		groceryUser.setGrocery(this.groceryDTO.get());
		groceryUser.setUser(this.user);
		groceryUser.setUserRole(this.userRole);
		groceryUser.setExpiryDate(this.expiryDate);

		return groceryUser;
	}

	public GroceryDTO getGroceryDTO() {
		return this.groceryDTO;
	}

	public String getUser() {
		return this.user;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public LocalDate getExpiryDate() {
		return this.expiryDate;
	}
}
