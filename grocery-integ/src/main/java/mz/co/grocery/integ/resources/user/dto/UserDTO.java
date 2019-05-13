/**
 *
 */
package mz.co.grocery.integ.resources.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

	public String getUuid() {
		return this.uuid;
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
}
