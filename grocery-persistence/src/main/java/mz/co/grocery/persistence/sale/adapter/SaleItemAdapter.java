/**
 *
 */
package mz.co.grocery.persistence.sale.adapter;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.grocery.persistence.sale.repository.SaleItemRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class SaleItemAdapter implements SaleItemPort {

	private SaleItemRepository repository;

	private EntityMapper<SaleItemEntity, SaleItem> mapper;

	public SaleItemAdapter(final SaleItemRepository repository,
			final EntityMapper<SaleItemEntity, SaleItem> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public SaleItem createSaleItem(final UserContext context, final SaleItem saleItem) throws BusinessException {
		final SaleItemEntity entity = this.mapper.toEntity(saleItem);

		this.repository.create(context, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public SaleItem updateSaleItem(final UserContext userContext, final SaleItem saleItem) throws BusinessException {
		final SaleItemEntity entity = this.mapper.toEntity(saleItem);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public SaleItem findByUuid(final String uuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(uuid));
	}
}
