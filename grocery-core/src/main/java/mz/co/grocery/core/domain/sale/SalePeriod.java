/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.time.LocalTime;

/**
 * @author Stélio Moiane
 *
 */
public enum SalePeriod {

	MORNING(LocalTime.of(0, 0), LocalTime.of(11, 59)),

	AFTER_NOON(LocalTime.of(12, 0), LocalTime.of(17, 59)),

	NIGHT(LocalTime.of(12, 0), LocalTime.of(17, 59));

	private LocalTime startTime;

	private LocalTime endTime;

	SalePeriod(final LocalTime startTime, final LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return this.startTime;
	}

	public LocalTime getEndTime() {
		return this.endTime;
	}
}
