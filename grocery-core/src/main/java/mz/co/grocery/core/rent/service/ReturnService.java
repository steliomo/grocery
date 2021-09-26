/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.util.List;

import mz.co.grocery.core.rent.model.ReturnItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ReturnService {

	List<ReturnItem> returnItems(UserContext userContext, List<ReturnItem> returnItems) throws BusinessException;

}
