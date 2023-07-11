/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ServiceEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class ServiceRepositoryImpl extends GenericDAOImpl<ServiceEntity, Long> implements ServiceRepository {

	private EntityMapper<ServiceEntity, Service> mapper;

	public ServiceRepositoryImpl(final EntityMapper<ServiceEntity, Service> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<Service> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(ServiceRepository.QUERY_NAME.findAll, new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList().stream().map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Service> findByName(final String serviceName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceRepository.QUERY_NAME.findByName,
				new ParamBuilder().add("serviceName", "%" + serviceName + "%").add("entityStatus", entityStatus).process()).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Service> findByUnit(final Unit unit, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceRepository.QUERY_NAME.findByUnit,
				new ParamBuilder().add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process()).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Service> findNotInThisUnit(final Unit unit, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceRepository.QUERY_NAME.findNotInThisUnit,
				new ParamBuilder().add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process()).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}
}
