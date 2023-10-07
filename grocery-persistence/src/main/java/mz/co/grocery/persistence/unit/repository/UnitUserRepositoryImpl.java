/**
 *
 */
package mz.co.grocery.persistence.unit.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.unit.UnitDetail;
import mz.co.grocery.persistence.unit.entity.UnitUserEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class UnitUserRepositoryImpl extends GenericDAOImpl<UnitUserEntity, Long> implements UnitUserRepository {

	@Override
	public List<UnitUserEntity> fetchAllGroceryUsers(final int currentPage, final int maxResult,
			final EntityStatus entityStatus) throws BusinessException {

		final List<Long> groceryUserIds = this
				.findByQuery(UnitUserRepository.QUERY_NAME.findAllIds,
						new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (groceryUserIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(UnitUserRepository.QUERY_NAME.fetchAll,
				new ParamBuilder().add("groceryUserIds", groceryUserIds).process());
	}

	@Override
	public UnitUserEntity fetchByUser(final String user, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(UnitUserRepository.QUERY_NAME.fetchByUser,
				new ParamBuilder().add("user", user).add("entityStatus", entityStatus).process());
	}

	@Override
	public UnitDetail findUnitDetailByUuid(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(UnitUserRepository.QUERY_NAME.findUnitDetailByUuid,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), UnitDetail.class);
	}
}