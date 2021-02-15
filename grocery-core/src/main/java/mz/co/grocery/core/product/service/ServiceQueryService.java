/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import mz.co.grocery.core.product.model.Service;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceQueryService {

	List<Service> findAllServices(int currentPage, int maxResult) throws BusinessException;

	Long countServices() throws BusinessException;

	Service findServiceByUuid(String serviceUuid) throws BusinessException;

	List<Service> findServicesByName(String serviceName) throws BusinessException;
}
