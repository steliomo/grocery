/**
 *
 */
package mz.co.grocery.core.application.rent.out;

import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RentItemPort {

	RentItem findByUuid(String uuid) throws BusinessException;

	RentItem updateRentItem(UserContext userContext, RentItem rentItem) throws BusinessException;

	RentItem createRentItem(UserContext userContext, RentItem rentItem) throws BusinessException;

	RentItem fetchByUuid(String uuid) throws BusinessException;
}
