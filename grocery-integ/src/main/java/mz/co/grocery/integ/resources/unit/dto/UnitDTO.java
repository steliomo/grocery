/**
 *
 */
package mz.co.grocery.integ.resources.unit.dto;

import java.time.LocalDate;

import mz.co.grocery.core.domain.unit.UnitType;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public class UnitDTO extends GenericDTO {

	private String name;

	private String address;

	private String phoneNumber;

	private String phoneNumberOptional;

	private String email;

	private UnitType unitType;

	private Integer numberOfTables;

	private String logoPath;

	private String signaturePath;

	private LocalDate subscriptionEndDate;

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

	public LocalDate getSubscriptionEndDate() {
		return this.subscriptionEndDate;
	}

	public void setSubscriptionEndDate(final LocalDate subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}
}
