/**
 *
 */
package mz.co.grocery.persistence.rent.adapter;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.rent.out.RentItemPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.persistence.rent.entity.RentItemEntity;
import mz.co.grocery.persistence.rent.repository.RentItemRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class RentItemAdapter implements RentItemPort {

	private RentItemRepository repository;

	private EntityMapper<RentItemEntity, RentItem> mapper;

	public RentItemAdapter(final RentItemRepository repository,
			final EntityMapper<RentItemEntity, RentItem> mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public RentItem findByUuid(final String uuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(uuid));
	}

	@Transactional
	@Override
	public RentItem updateRentItem(final UserContext userContext, final RentItem rentItem) throws BusinessException {
		final RentItemEntity entity = this.mapper.toEntity(rentItem);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Transactional
	@Override
	public RentItem createRentItem(final UserContext userContext, final RentItem rentItem) throws BusinessException {
		final RentItemEntity entity = this.mapper.toEntity(rentItem);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public RentItem fetchByUuid(final String uuid) throws BusinessException {
		return this.repository.fetchByUuid(uuid);
	}
}
