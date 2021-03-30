/**
 *
 */
package mz.co.grocery.core.item.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.dao.ServiceDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceQueryServiceImpl.NAME)
public class ServiceQueryServiceImpl implements ServiceQueryService {

	public static final String NAME = "mz.co.grocery.core.item.service.ServiceQueryServiceImpl";

	@Inject
	private ServiceDAO serviceDAO;

	@Override
	public List<mz.co.grocery.core.item.model.Service> findAllServices(final int currentPage, final int maxResult) throws BusinessException {

		return this.serviceDAO.findAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countServices() throws BusinessException {
		return this.serviceDAO.count();
	}

	@Override
	public mz.co.grocery.core.item.model.Service findServiceByUuid(final String serviceUuid) throws BusinessException {
		return this.serviceDAO.findByUuid(serviceUuid);
	}

	@Override
	public List<mz.co.grocery.core.item.model.Service> findServicesByName(final String serviceName) throws BusinessException {
		return this.serviceDAO.findByName(serviceName, EntityStatus.ACTIVE);
	}

	@Override
	public List<mz.co.grocery.core.item.model.Service> findServicesByUnit(final Grocery unit) throws BusinessException {
		return this.serviceDAO.findByUnit(unit, EntityStatus.ACTIVE);
	}

	@Override
	public List<mz.co.grocery.core.item.model.Service> findServicesNotInthisUnit(final Grocery unit) throws BusinessException {
		return this.serviceDAO.findNotInThisUnit(unit, EntityStatus.ACTIVE);
	}
}
