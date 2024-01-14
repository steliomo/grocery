/**
 *
 */
package mz.co.grocery.persistence.unit.entity;

import java.math.BigDecimal;

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

import mz.co.grocery.core.domain.unit.UnitType;
import mz.co.grocery.persistence.unit.repository.UnitRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = UnitRepository.QUERY_NAME.findAllIds, query = UnitRepository.QUERY.findAllIds),
	@NamedQuery(name = UnitRepository.QUERY_NAME.findAll, query = UnitRepository.QUERY.findAll),
	@NamedQuery(name = UnitRepository.QUERY_NAME.findByName, query = UnitRepository.QUERY.findByName) })
@Entity
@Table(name = "GROCERIES")
public class UnitEntity extends GenericEntity {

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

	@NotNull
	@Column(name = "BALANCE", nullable = false)
	private BigDecimal balance = BigDecimal.ZERO;

	@NotNull
	@Column(name = "NUMBER_OF_TABLES", nullable = false)
	private Integer numberOfTables = BigDecimal.ZERO.intValue();

	@Column(name = "LOGO_PATH", length = 50)
	private String logoPath;

	@Column(name = "SIGNATURE_PATH", length = 50)
	private String signaturePath;

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

	public void setBalance(final BigDecimal balance) {
		this.balance = this.balance.add(balance);
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	public synchronized void debitTransaction(final BigDecimal defaultDebit) {
		this.balance = this.balance.subtract(defaultDebit);
	}

	public Integer getNumberOfTables() {
		return this.numberOfTables;
	}

	public void setNumberOfTables(final Integer numberOfTables) {
		this.numberOfTables = numberOfTables;
	}

	public String getLogoPath() {
		return this.logoPath;
	}

	public void setLogoPath(final String logoPath) {
		this.logoPath = logoPath;
	}

	public String getSignaturePath() {
		return this.signaturePath;
	}

	public void setSignaturePath(final String signaturePath) {
		this.signaturePath = signaturePath;
	}
}
