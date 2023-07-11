/**
 *
 */
package mz.co.grocery.core.application.unit.out;

import java.util.List;

import mz.co.grocery.core.domain.unit.UnitDetail;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface UnitUserPort {

	UnitUser createUnitUser(UserContext userContext, UnitUser groceryUser) throws BusinessException;

	List<UnitUser> fetchAllUnitUsers(int currentPage, int maxResult) throws BusinessException;

	Long count() throws BusinessException;

	UnitUser fetchUnitUserByUser(String user) throws BusinessException;

	UnitDetail findUnitDetailsByUuid(String unitUuid) throws BusinessException;
}
