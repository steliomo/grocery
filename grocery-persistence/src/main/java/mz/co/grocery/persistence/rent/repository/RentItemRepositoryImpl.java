/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.rent.entity.RentItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class RentItemRepositoryImpl extends GenericDAOImpl<RentItemEntity, Long> implements RentItemRepository {

	@Override
	public RentItemEntity fetchByUuid(final String rentItemUuid) throws BusinessException {
		return this.findSingleByNamedQuery(RentItemRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("rentItemUuid", rentItemUuid).process());
	}
}
