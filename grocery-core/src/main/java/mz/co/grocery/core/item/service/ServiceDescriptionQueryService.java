/**
 *
 */
package mz.co.grocery.core.item.service;

import java.util.List;

import mz.co.grocery.core.item.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceDescriptionQueryService {

	List<ServiceDescription> findAllServiceDescriptions(int currentPage, int maxResult) throws BusinessException;

	Long countServiceDescriptions() throws BusinessException;

	ServiceDescription fetchServiceDescriptionByUuid(String serviceDescriptionUuid) throws BusinessException;

	List<ServiceDescription> fetchServiceDescriptionByName(String serviceDescriptionName) throws BusinessException;

}
