/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.application.sale.in.SendDailySalesReportUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.sale.out.SalePaymentPort;
import mz.co.grocery.core.application.sale.out.SalePort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.grocery.core.domain.email.EmailType;
import mz.co.grocery.core.domain.sale.SaleItemReport;
import mz.co.grocery.core.domain.sale.SaleReportDocument;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class SendDailySalesReportService extends AbstractService implements SendDailySalesReportUseCase {

	private UnitPort unitPort;

	private SaleItemPort saleItemPort;

	private DocumentGeneratorPort documentGeneratorPort;

	private EmailPort emailPort;

	private SalePort salePort;

	private SalePaymentPort salePaymentPort;

	public SendDailySalesReportService(final UnitPort unitPort, final SaleItemPort saleItemPort, final DocumentGeneratorPort documentGeneratorPort,
			final EmailPort emailPort, final SalePort salePort, final SalePaymentPort salePaymentPort) {
		this.unitPort = unitPort;
		this.saleItemPort = saleItemPort;
		this.documentGeneratorPort = documentGeneratorPort;
		this.emailPort = emailPort;
		this.salePort = salePort;
		this.salePaymentPort = salePaymentPort;
	}

	@Override
	public List<Unit> sendReport(final LocalDate saleDate) throws BusinessException {

		final List<Unit> units = this.unitPort.findUnitsWithDailySales(saleDate);

		for (final Unit unit : units) {

			final List<SaleItemReport> saleItems = this.saleItemPort.findSaleItemsByUnitAndPeriod(unit.getUuid(), saleDate, saleDate);

			final BigDecimal totalCash = this.salePort.findTotalCashByUnitAndPeriod(unit.getUuid(), saleDate, saleDate).orElse(BigDecimal.ZERO);
			final BigDecimal totalCredit = this.salePort.findTotalCreditByUnitAndPeriod(unit.getUuid(), saleDate, saleDate).orElse(BigDecimal.ZERO);
			final BigDecimal debtCollections = this.salePaymentPort.findDebtCollectionsByUnitAndPeriod(unit.getUuid(), saleDate, saleDate).orElse(BigDecimal.ZERO);

			final Document document = new SaleReportDocument(unit, saleDate, saleItems, totalCash, totalCredit, debtCollections);

			final String filename = document.getFilename();

			this.documentGeneratorPort.generatePdfDocument(document);

			final EmailDetails email = new EmailDetails(unit, EmailType.DAILY_SALE, filename);
			email.setParam("saleDate", saleDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			email.setParam("unitName", unit.getName());
			email.setParam("phoneNumber", unit.getPhoneNumber());

			this.emailPort.send(email);
		}

		return units;
	}
}
