/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.time.LocalTime;

import mz.co.grocery.core.domain.sale.SalePeriod;

/**
 * @author Stélio Moiane
 *
 */
public class SalePeriodIdentifierUtil {

	public static SalePeriod identify(final LocalTime period) {

		if (!period.isBefore(SalePeriod.MORNING.getStartTime()) && !period.isAfter(SalePeriod.MORNING.getEndTime())) {
			return SalePeriod.MORNING;
		}

		if (!period.isBefore(SalePeriod.AFTER_NOON.getStartTime()) && !period.isAfter(SalePeriod.AFTER_NOON.getEndTime())) {
			return SalePeriod.AFTER_NOON;
		}

		return SalePeriod.NIGHT;
	}
}
