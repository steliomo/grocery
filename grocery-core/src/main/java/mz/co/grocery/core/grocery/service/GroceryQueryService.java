/**
 *
 */
package mz.co.grocery.core.grocery.service;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface GroceryQueryService {

	List<Grocery> findAllGroceries(int currentPage, int maxResult) throws BusinessException;

	Long count() throws BusinessException;

	Grocery findByUuid(String groceryUuid) throws BusinessException;

}
