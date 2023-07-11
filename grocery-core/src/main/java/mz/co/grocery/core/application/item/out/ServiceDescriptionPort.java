/**
 *
 */
package mz.co.grocery.core.application.item.out;

import java.util.List;

import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionPort {

	ServiceDescription createServiceDescription(UserContext userContext, ServiceDescription serviceDescription)
			throws BusinessException;

	ServiceDescription updateServiceDescription(UserContext userContext, ServiceDescription serviceDescription) throws BusinessException;

	List<ServiceDescription> findAllServiceDescriptions(int currentPage, int maxResult) throws BusinessException;

	Long countServiceDescriptions() throws BusinessException;

	ServiceDescription fetchServiceDescriptionByUuid(String serviceDescriptionUuid) throws BusinessException;

	List<ServiceDescription> fetchServiceDescriptionByName(String serviceDescriptionName) throws BusinessException;

}
