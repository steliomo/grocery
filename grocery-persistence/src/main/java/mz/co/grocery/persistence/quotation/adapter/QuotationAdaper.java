/**
 *
 */
package mz.co.grocery.persistence.quotation.adapter;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationPort;
import mz.co.grocery.core.application.report.ReportGeneratorPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.persistence.quotation.entity.QuotationEntity;
import mz.co.grocery.persistence.quotation.entity.QuotationReport;
import mz.co.grocery.persistence.quotation.repository.QuotationRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class QuotationAdaper implements SaveQuotationPort, GenerateQuotationPdfPort {

	private QuotationRepository quotationRepository;

	private EntityMapper<QuotationEntity, Quotation> mapper;

	private ReportGeneratorPort reportGeneratorPort;

	public QuotationAdaper(final QuotationRepository quotationRepository, final ReportGeneratorPort reportGeneratorPort,
			final EntityMapper<QuotationEntity, Quotation> mapper) {
		this.quotationRepository = quotationRepository;
		this.reportGeneratorPort = reportGeneratorPort;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Quotation save(final UserContext context, final Quotation quotation) throws BusinessException {
		final QuotationEntity quotationEntity = this.mapper.toEntity(quotation);

		this.quotationRepository.create(context, quotationEntity);

		quotation.setId(quotationEntity.getId());
		quotation.setUuid(quotationEntity.getUuid());
		return quotation;
	}

	@Override
	public String generatePdf(final Quotation quotation, final LocalDateTime quotationDataTime) throws BusinessException {
		final QuotationReport quotationReport = new QuotationReport(quotation, quotationDataTime);
		this.reportGeneratorPort.createPdfReport(quotationReport);
		return quotationReport.getFilePath();
	}
}
