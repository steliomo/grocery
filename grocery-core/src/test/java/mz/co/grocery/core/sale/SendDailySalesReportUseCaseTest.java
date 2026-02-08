/**
 *
 */
package mz.co.grocery.core.sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.sale.service.SendDailySalesReportService;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SendDailySalesReportUseCaseTest extends AbstractUnitServiceTest {

	@InjectMocks
	private SendDailySalesReportService sendDailySalesReportUseCase;

	@Mock
	private UnitPort unitPort;

	@Mock
	private SaleItemPort saleItemPort;

	@Mock
	private DocumentGeneratorPort documentGeneratorPort;

	@Mock
	private EmailPort emailPort;

	@Mock
	private SalePort salePort;

	@Mock
	private SalePaymentPort salePaymentPort;

	@Mock
	private ApplicationTranslator translator;

	@Test
	public void shouldSendDailySalesReport() throws BusinessException {

		final LocalDate saleDate = LocalDate.now();

		Mockito.when(this.unitPort.findUnitsWithDailySales(saleDate)).thenReturn(EntityFactory.gimme(Unit.class, 5, UnitTemplate.VALID));

		Mockito.when(this.salePort.findDailyNumberOfSalesAndTotalSalesByUnit(ArgumentMatchers.any(), ArgumentMatchers.any()))
		.thenReturn(Optional.of(new SaleReport(LocalDate.now(), 1L, BigDecimal.ONE)));

		final List<Unit> units = this.sendDailySalesReportUseCase.sendReport(saleDate);

		Assert.assertFalse(units.isEmpty());
	}
}
