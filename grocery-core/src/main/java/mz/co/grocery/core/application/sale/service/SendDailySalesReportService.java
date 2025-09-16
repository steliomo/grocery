/**
 *
 */
package mz.co.grocery.core.application.sale.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.application.sale.in.SendDailySalesReportUseCase;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.grocery.core.domain.email.EmailType;
import mz.co.grocery.core.domain.sale.SaleItemReport;
import mz.co.grocery.core.domain.sale.SaleReportDocument;
import mz.co.grocery.core.domain.sale.SaleStatus;
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

	public SendDailySalesReportService(final UnitPort unitPort, final SaleItemPort saleItemPort, final DocumentGeneratorPort documentGeneratorPort,
			final EmailPort emailPort) {
		this.unitPort = unitPort;
		this.saleItemPort = saleItemPort;
		this.documentGeneratorPort = documentGeneratorPort;
		this.emailPort = emailPort;
	}

	@Override
	public List<Unit> sendReport(final LocalDate saleDate) throws BusinessException {

		final List<Unit> units = this.unitPort.findUnitsWithDailySales(saleDate);

		for (final Unit unit : units) {

			final List<SaleItemReport> saleItems = this.saleItemPort.findSaleItemsByUnitAndPeriodAndSaleStatus(unit.getUuid(), saleDate, saleDate,
					SaleStatus.CLOSED);

			final Document document = new SaleReportDocument(unit, saleDate, saleItems);

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
