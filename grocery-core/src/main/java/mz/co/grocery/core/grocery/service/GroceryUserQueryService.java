/**
 *
 */
package mz.co.grocery.core.grocery.service;

import java.util.List;

import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface GroceryUserQueryService {

	List<GroceryUser> fetchAllGroceryUsers(int currentPage, int maxResult) throws BusinessException;

	Long count() throws BusinessException;

	GroceryUser fetchGroceryUserByUser(String user) throws BusinessException;
}
