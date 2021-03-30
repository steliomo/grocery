/**
 *
 */
package mz.co.grocery.core.item.service;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.Service;
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

	List<Service> findServicesByUnit(Grocery unit) throws BusinessException;

	List<Service> findServicesNotInthisUnit(Grocery unit) throws BusinessException;
}
