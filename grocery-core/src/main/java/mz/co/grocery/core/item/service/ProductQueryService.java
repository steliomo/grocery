/**
 *
 */
package mz.co.grocery.core.item.service;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.Product;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductQueryService {

	List<Product> findAllProducts() throws BusinessException;

	List<Product> findProductByName(String name) throws BusinessException;

	Product findProductByUuid(String uuid) throws BusinessException;

	List<Product> findProductsByGrocery(Grocery grocery) throws BusinessException;

	List<Product> findProductsNotInThisGrocery(Grocery grocery) throws BusinessException;
}
