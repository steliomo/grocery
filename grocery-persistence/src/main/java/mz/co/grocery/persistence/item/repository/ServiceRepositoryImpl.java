/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ServiceEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class ServiceRepositoryImpl extends GenericDAOImpl<ServiceEntity, Long> implements ServiceRepository {

	@Override
	public List<ServiceEntity> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(ServiceRepository.QUERY_NAME.findAll, new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public List<ServiceEntity> findByName(final String serviceName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceRepository.QUERY_NAME.findByName,
				new ParamBuilder().add("serviceName", "%" + serviceName + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public List<ServiceEntity> findByUnit(final Unit unit, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceRepository.QUERY_NAME.findByUnit,
				new ParamBuilder().add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<ServiceEntity> findNotInThisUnit(final Unit unit, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceRepository.QUERY_NAME.findNotInThisUnit,
				new ParamBuilder().add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process());
	}
}
