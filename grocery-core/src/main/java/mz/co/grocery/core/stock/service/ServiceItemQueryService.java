/**
 *
 */
package mz.co.grocery.core.stock.service;

import java.util.List;

import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceItemQueryService {

	List<ServiceItem> fetchAllServiceItems(int currentPage, int maxResult) throws BusinessException;

	Long countServiceItems() throws BusinessException;

	ServiceItem fetchServiceItemByUuid(String serviceItemUuid) throws BusinessException;

}
