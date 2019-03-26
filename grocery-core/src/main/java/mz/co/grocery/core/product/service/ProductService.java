/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.Product;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductService {
	Product createProduct(UserContext userContext, Product product) throws BusinessException;
}
