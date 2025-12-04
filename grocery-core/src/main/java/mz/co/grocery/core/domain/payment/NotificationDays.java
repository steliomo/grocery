/**
 *
 */
package mz.co.grocery.core.domain.payment;

/**
 * @author Stélio Moiane
 *
 */
public enum NotificationDays {

	THREE_DAYS(3),

	FIVE_DAYS(5);

	private int value;

	NotificationDays(final int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
