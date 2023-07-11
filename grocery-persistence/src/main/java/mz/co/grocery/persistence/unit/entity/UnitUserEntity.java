/**
 *
 */
package mz.co.grocery.persistence.unit.entity;

import java.time.LocalDate;
import java.util.Optional;

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

import mz.co.grocery.core.domain.unit.UserRole;
import mz.co.grocery.persistence.unit.repository.UnitUserRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({ @NamedQuery(name = UnitUserRepository.QUERY_NAME.findAllIds, query = UnitUserRepository.QUERY.findAllIds),
	@NamedQuery(name = UnitUserRepository.QUERY_NAME.fetchAll, query = UnitUserRepository.QUERY.fetchAll),
	@NamedQuery(name = UnitUserRepository.QUERY_NAME.fetchByUser, query = UnitUserRepository.QUERY.fetchByUser),
	@NamedQuery(name = UnitUserRepository.QUERY_NAME.findUnitDetailByUuid, query = UnitUserRepository.QUERY.findUnitDetailByUuid) })
@Entity
@Table(name = "GROCERY_USERS")
public class UnitUserEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROCERY_ID", nullable = false)
	private UnitEntity unit;

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

	public Optional<UnitEntity> getUnit() {
		return Optional.ofNullable(this.unit);
	}

	public void setUnit(final UnitEntity unit) {
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
