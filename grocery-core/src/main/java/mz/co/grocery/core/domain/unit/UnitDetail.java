/**
 *
 */
package mz.co.grocery.core.domain.unit;

import java.math.BigDecimal;

/**
 * @author Stélio Moiane
 *
 */
public class UnitDetail {

	private final String uuid;

	private final String name;

	private final Long users;

	private final BigDecimal balance;

	public UnitDetail(final String uuid, final String name, final Long users, final BigDecimal balance) {
		this.uuid = uuid;
		this.name = name;
		this.users = users;
		this.balance = balance;
	}

	public String getUuid() {
		return this.uuid;
	}

	public String getName() {
		return this.name;
	}

	public Long getUsers() {
		return this.users;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}
}
