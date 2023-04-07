/**
 *
 */
package mz.co.grocery.persistence.quotation.adapter;

import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.persistence.quotation.dao.QuotationRepository;
import mz.co.grocery.persistence.quotation.model.QuotationEntity;
import mz.co.grocery.persistence.quotation.model.QuotationMapper;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class QuotationPersistenceAdaper implements SaveQuotationPort, GenerateQuotationPdfPort {

	private QuotationRepository quotationRepository;

	private QuotationMapper quotationMapper;

	public QuotationPersistenceAdaper(final QuotationRepository quotationRepository, final QuotationMapper quotationMapper) {
		this.quotationRepository = quotationRepository;
		this.quotationMapper = quotationMapper;
	}

	@Override
	public Quotation save(final UserContext context, final Quotation quotation) throws BusinessException {
		final QuotationEntity quotationEntity = this.quotationMapper.mapToEntity(quotation);

		this.quotationRepository.create(context, quotationEntity);

		return quotation;
	}

	@Override
	public void generatePdf(final Quotation quotation) throws BusinessException {

	}
}
