/**
 *
 */
package mz.co.grocery.persistence.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import mz.co.grocery.core.application.sale.in.SendDailySalesReportUseCase;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class SendDailyReportUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private SaleBuilder saleBuilder;

	@Inject
	private SendDailySalesReportUseCase sendDailySalesReportUseCase;

	@Test
	public void shouldSendDailyReport() throws BusinessException {

		this.saleBuilder.sale().withProducts(5).withCustomer().withUnit().saleType(SaleType.CASH)
		.withTotal(new BigDecimal(1000)).withTotalPaid(new BigDecimal(1000)).dueDate(LocalDate.now()).build();

		final List<Unit> units = this.sendDailySalesReportUseCase.sendReport(LocalDate.now());

		Assert.assertFalse(units.isEmpty());
	}
}
