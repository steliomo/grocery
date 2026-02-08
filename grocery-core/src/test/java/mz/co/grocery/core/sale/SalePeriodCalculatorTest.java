/**
 *
 */
package mz.co.grocery.core.sale;

import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.sale.service.SalePeriodIdentifierUtil;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.sale.SalePeriod;

/**
 * @author Stélio Moiane
 *
 */

public class SalePeriodCalculatorTest extends AbstractUnitServiceTest {

	@Test
	public void shouldFindSalePeriod() {

		final SalePeriod morning = SalePeriodIdentifierUtil.identify(LocalTime.of(07, 45));
		final SalePeriod afternoon = SalePeriodIdentifierUtil.identify(LocalTime.of(12, 30));
		final SalePeriod nigh = SalePeriodIdentifierUtil.identify(LocalTime.of(20, 59));

		Assert.assertEquals(SalePeriod.MORNING, morning);
		Assert.assertEquals(SalePeriod.AFTER_NOON, afternoon);
		Assert.assertEquals(SalePeriod.NIGHT, nigh);
	}
}
