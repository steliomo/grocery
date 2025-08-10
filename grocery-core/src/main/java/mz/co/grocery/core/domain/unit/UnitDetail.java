/**
 *
 */
package mz.co.grocery.core.domain.unit;

import java.time.LocalDate;

/**
 * @author Stélio Moiane
 *
 */
public class UnitDetail {

	private final String uuid;

	private final String name;

	private final Long users;

	private final LocalDate subscriptionEndDate;

	public UnitDetail(final String uuid, final String name, final Long users, final LocalDate subscriptionEndDate) {
		this.uuid = uuid;
		this.name = name;
		this.users = users;
		this.subscriptionEndDate = subscriptionEndDate;
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

	public LocalDate getSubscriptionEndDate() {
		return this.subscriptionEndDate;
	}
}
