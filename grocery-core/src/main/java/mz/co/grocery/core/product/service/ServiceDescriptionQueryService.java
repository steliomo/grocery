/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionQueryService {

	List<ServiceDescription> findAllServiceDescriptions(int currentPage, int maxResult) throws BusinessException;

	Long countServiceDescriptions() throws BusinessException;

	ServiceDescription fetchServiceDescriptionByUuid(String serviceDescriptionUuid) throws BusinessException;

}
