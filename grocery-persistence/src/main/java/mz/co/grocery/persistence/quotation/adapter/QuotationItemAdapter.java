/**
 *
 */
package mz.co.grocery.persistence.quotation.adapter;

import mz.co.grocery.core.application.quotation.out.SaveQuotationItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.persistence.quotation.entity.QuotationItemEntity;
import mz.co.grocery.persistence.quotation.repository.QuotationItemRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class QuotationItemAdapter implements SaveQuotationItemPort {

	private EntityMapper<QuotationItemEntity, QuotationItem> mapper;
	private QuotationItemRepository quotationItemRepository;

	public QuotationItemAdapter(final QuotationItemRepository quotationItemRepository,
			final EntityMapper<QuotationItemEntity, QuotationItem> mapper) {
		this.quotationItemRepository = quotationItemRepository;
		this.mapper = mapper;
	}

	@Override
	public QuotationItem save(final UserContext context, final QuotationItem quotationItem) throws BusinessException {
		final QuotationItemEntity entity = this.mapper.toEntity(quotationItem);

		this.quotationItemRepository.create(context, entity);

		quotationItem.setId(entity.getId());
		quotationItem.setUuid(entity.getUuid());

		return quotationItem;
	}
}
