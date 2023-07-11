/**
 *
 */
package mz.co.grocery.core.application.sale.out;

import java.util.List;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ServiceItemPort {

	ServiceItem createServiceItem(UserContext userContext, ServiceItem serviceItem) throws BusinessException;

	ServiceItem updateServiceItem(UserContext userContext, ServiceItem serviceItem) throws BusinessException;

	List<ServiceItem> fetchAllServiceItems(int currentPage, int maxResult) throws BusinessException;

	Long countServiceItems() throws BusinessException;

	ServiceItem fetchServiceItemByUuid(String serviceItemUuid) throws BusinessException;

	List<ServiceItem> fetchServiceItemByName(String serviceItemName) throws BusinessException;

	List<ServiceItem> fetchServiceItemsByServiceAndUnit(Service service, Unit unit) throws BusinessException;

	List<ServiceItem> fetchServiceItemsNotInThisUnitByService(Service service, Unit unit) throws BusinessException;

	ServiceItem findsaleItemById(Long id) throws BusinessException;

}
