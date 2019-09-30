/**
 *
 */
package mz.co.grocery.integ.resources.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import mz.co.grocery.core.grocery.model.GroceryUser;

/**
 * @author Stélio Moiane
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

	private String uuid;

	private String username;

	private String password;

	private String createdBy;

	private String fullName;

	private GroceryUser groceryUser;

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public GroceryUser getGroceryUser() {
		return this.groceryUser;
	}

	public void setGroceryUser(final GroceryUser groceryUser) {
		this.groceryUser = groceryUser;
	}
}
