/**
 *
 */
package mz.co.grocery.core.item.service;

import mz.co.grocery.core.item.model.Product;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductService {

	Product createProduct(UserContext userContext, Product product) throws BusinessException;

	Product uppdateProduct(UserContext userContext, Product product) throws BusinessException;

	Product removeProduct(UserContext userContext, Product product) throws BusinessException;
}
