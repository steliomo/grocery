/**
 *
 */
package mz.co.grocery.core.grocery.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.grocery.dao.GroceryUserDAO;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = GroceryUserDAO.QUERY_NAME.findAllIds, query = GroceryUserDAO.QUERY.findAllIds),
	@NamedQuery(name = GroceryUserDAO.QUERY_NAME.fetchAll, query = GroceryUserDAO.QUERY.fetchAll),
	@NamedQuery(name = GroceryUserDAO.QUERY_NAME.fetchByUser, query = GroceryUserDAO.QUERY.fetchByUser),
	@NamedQuery(name = GroceryUserDAO.QUERY_NAME.findUnitDetailByUuid, query = GroceryUserDAO.QUERY.findUnitDetailByUuid) })
@Entity
@Table(name = "GROCERY_USERS")
public class GroceryUser extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private Grocery grocery;

	@NotEmpty
	@Column(name = "USER_UUID", nullable = false, length = 50)
	private String user;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "USER_ROLE", nullable = false, length = 30)
	private UserRole userRole;

	@NotNull
	@Column(name = "EXPIRY_DATE", nullable = false)
	private LocalDate expiryDate;

	public Grocery getGrocery() {
		return this.grocery;
	}

	public void setGrocery(final Grocery grocery) {
		this.grocery = grocery;
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
