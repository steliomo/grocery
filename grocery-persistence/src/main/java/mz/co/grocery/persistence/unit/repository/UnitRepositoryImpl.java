/**
 *
 */
package mz.co.grocery.persistence.unit.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class UnitRepositoryImpl extends GenericDAOImpl<UnitEntity, Long> implements UnitRepository {

	@Override
	public List<UnitEntity> findAll(final int currentPage, final int maxReult, final EntityStatus entityStatus)
			throws BusinessException {

		final List<Long> unitIds = this
				.findByQuery(UnitRepository.QUERY_NAME.findAllIds,
						new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class)
				.setFirstResult(currentPage * maxReult).setMaxResults(maxReult).getResultList();

		if (unitIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(UnitRepository.QUERY_NAME.findAll,
				new ParamBuilder().add("unitIds", unitIds).process());
	}

	@Override
	public List<UnitEntity> findByName(final String unitName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(UnitRepository.QUERY_NAME.findByName,
				new ParamBuilder().add("unitName", "%" + unitName + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public List<UnitEntity> findUnitsWithDailySales(final LocalDate saleDate, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(UnitRepository.QUERY_NAME.findUnitsWithDailySales,
				new ParamBuilder().add("saleDate", saleDate).add("entityStatus", entityStatus).process());
	}
}
