/**
 *
 */
package mz.co.grocery.core.grocery.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.grocery.dao.GroceryDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = GroceryDAO.QUERY_NAME.findAllIds, query = GroceryDAO.QUERY.findAllIds),
	@NamedQuery(name = GroceryDAO.QUERY_NAME.findAll, query = GroceryDAO.QUERY.findAll),
	@NamedQuery(name = GroceryDAO.QUERY_NAME.findByName, query = GroceryDAO.QUERY.findByName) })
@Entity
@Table(name = "GROCERIES")
public class Grocery extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(name = "NAME", nullable = false, length = 140)
	private String name;

	@NotEmpty
	@Column(name = "ADDRESS", nullable = false)
	private String address;

	@NotEmpty
	@Column(name = "PHONE_NUMBER", nullable = false, length = 30)
	private String phoneNumber;

	@Column(name = "PHONE_NUMBER_OPTIONAL", length = 30)
	private String phoneNumberOptional;

	@Email
	@Column(name = "EMAIL", length = 50)
	private String email;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "UNIT_TYPE", nullable = false, length = 30)
	private UnitType unitType;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumberOptional() {
		return this.phoneNumberOptional;
	}

	public void setPhoneNumberOptional(final String phoneNumberOptional) {
		this.phoneNumberOptional = phoneNumberOptional;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public UnitType getUnitType() {
		return this.unitType;
	}

	public void setUnitType(final UnitType unitType) {
		this.unitType = unitType;
	}
}
