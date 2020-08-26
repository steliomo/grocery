/**
 *
 */
package mz.co.grocery.integ.resources.user.dto;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mz.co.grocery.integ.resources.grocery.dto.GroceryUserDTO;

/**
 * @author Stélio Moiane
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class UserDTO {

	private String uuid;

	private String username;

	private String password;

	private String createdBy;

	private String fullName;

	private GroceryUserDTO groceryUserDTO;

	private String email;

	private Map<String, Object> properties;

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

	public GroceryUserDTO getGroceryUserDTO() {
		return this.groceryUserDTO;
	}

	public void setGroceryUserDTO(final GroceryUserDTO groceryUserDTO) {
		this.groceryUserDTO = groceryUserDTO;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setProperties(final String key, final Object value) {

		if (this.properties == null) {
			this.properties = new HashMap<>();
		}

		this.properties.put(key, value);
	}

	public Map<String, Object> getProperties() {
		return this.properties;
	}
}
