/**
 *
 */
package mz.co.grocery.core.application.item.out;

import java.util.List;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServicePort {

	Service createService(UserContext userContext, Service service) throws BusinessException;

	Service updateService(UserContext userContext, Service service) throws BusinessException;

	List<Service> findAllServices(int currentPage, int maxResult) throws BusinessException;

	Long countServices() throws BusinessException;

	Service findServiceByUuid(String serviceUuid) throws BusinessException;

	List<Service> findServicesByName(String serviceName) throws BusinessException;

	List<Service> findServicesByUnit(Unit unit) throws BusinessException;

	List<Service> findServicesNotInthisUnit(Unit unit) throws BusinessException;
}
