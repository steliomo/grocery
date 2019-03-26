/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.ProductSize;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductSizeService {

	ProductSize createProductSize(UserContext userContext, ProductSize productSize) throws BusinessException;

}
