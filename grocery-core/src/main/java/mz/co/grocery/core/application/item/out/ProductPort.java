/**
 *
 */
package mz.co.grocery.core.application.item.out;

import java.util.List;

import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductPort {

	Product createProduct(UserContext userContext, Product product) throws BusinessException;

	Product uppdateProduct(UserContext userContext, Product product) throws BusinessException;

	Product removeProduct(UserContext userContext, Product product) throws BusinessException;

	List<Product> findAllProducts() throws BusinessException;

	List<Product> findProductByName(String name) throws BusinessException;

	Product findProductByUuid(String uuid) throws BusinessException;

	List<Product> findProductsByGrocery(Unit grocery) throws BusinessException;

	List<Product> findProductsNotInThisGrocery(Unit grocery) throws BusinessException;
}
