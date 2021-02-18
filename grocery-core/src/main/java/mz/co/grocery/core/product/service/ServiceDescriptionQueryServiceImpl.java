/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ServiceDescriptionDAO;
import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ServiceDescriptionQueryServiceImpl.NAME)
public class ServiceDescriptionQueryServiceImpl implements ServiceDescriptionQueryService {

	public static final String NAME = "mz.co.grocery.core.product.service.ServiceDescriptionQueryServiceImpl";

	@Inject
	private ServiceDescriptionDAO serviceDescriptionDAO;

	@Override
	public List<ServiceDescription> findAllServiceDescriptions(final int currentPage, final int maxResult) throws BusinessException {
		return this.serviceDescriptionDAO.findAll(currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countServiceDescriptions() throws BusinessException {
		return this.serviceDescriptionDAO.count();
	}

	@Override
	public ServiceDescription fetchServiceDescriptionByUuid(final String serviceDescriptionUuid) throws BusinessException {
		return this.serviceDescriptionDAO.fetchByUuid(serviceDescriptionUuid);
	}

	@Override
	public List<ServiceDescription> fetchServiceDescriptionByName(final String serviceDescriptionName) throws BusinessException {
		return this.serviceDescriptionDAO.fetchByName(serviceDescriptionName, EntityStatus.ACTIVE);
	}
}
