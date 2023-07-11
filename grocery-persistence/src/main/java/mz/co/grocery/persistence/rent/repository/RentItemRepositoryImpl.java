/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.persistence.rent.entity.RentItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class RentItemRepositoryImpl extends GenericDAOImpl<RentItemEntity, Long> implements RentItemRepository {

	private EntityMapper<RentItemEntity, RentItem> mapper;

	public RentItemRepositoryImpl(final EntityMapper<RentItemEntity, RentItem> mapper) {
		this.mapper = mapper;
	}

	@Override
	public RentItem fetchByUuid(final String rentItemUuid) throws BusinessException {
		return this.mapper.toDomain(this.findSingleByNamedQuery(RentItemRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("rentItemUuid", rentItemUuid).process()));
	}
}
