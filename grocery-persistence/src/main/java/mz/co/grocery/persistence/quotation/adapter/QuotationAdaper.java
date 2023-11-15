/**
 *
 */
package mz.co.grocery.persistence.quotation.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.QuotationPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.persistence.quotation.entity.QuotationEntity;
import mz.co.grocery.persistence.quotation.entity.QuotationReport;
import mz.co.grocery.persistence.quotation.repository.QuotationRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class QuotationAdaper implements SaveQuotationPort, GenerateQuotationPdfPort, QuotationPort {

	private QuotationRepository repository;

	private EntityMapper<QuotationEntity, Quotation> mapper;

	private DocumentGeneratorPort reportGeneratorPort;

	public QuotationAdaper(final QuotationRepository quotationRepository, final DocumentGeneratorPort reportGeneratorPort,
			final EntityMapper<QuotationEntity, Quotation> mapper) {
		this.repository = quotationRepository;
		this.reportGeneratorPort = reportGeneratorPort;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Quotation save(final UserContext context, final Quotation quotation) throws BusinessException {
		final QuotationEntity quotationEntity = this.mapper.toEntity(quotation);

		this.repository.create(context, quotationEntity);

		quotation.setId(quotationEntity.getId());
		quotation.setUuid(quotationEntity.getUuid());
		return quotation;
	}

	@Override
	public String generatePdf(final Quotation quotation, final LocalDateTime quotationDataTime) throws BusinessException {
		final QuotationReport quotationReport = new QuotationReport(quotation, quotationDataTime);
		this.reportGeneratorPort.generatePdfDocument(quotationReport);
		return quotationReport.getFilename();
	}

	@Override
	public List<Quotation> fetchQuotationsByCustomer(final String customerUuid) throws BusinessException {
		return this.repository.fetchQuotationsByCustomer(customerUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}
}
