/**
 *
 */
package mz.co.grocery.core.domain.unit;

import java.math.BigDecimal;

import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */
public class Unit extends Domain {

	private String name;

	private String address;

	private String phoneNumber;

	private String phoneNumberOptional;

	private String email;

	private UnitType unitType;

	private BigDecimal balance;

	public Unit() {
		this.balance = BigDecimal.ZERO;
	}

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
}
