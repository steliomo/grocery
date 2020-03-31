/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductDescriptionService {

	ProductDescription createProductDescription(UserContext userContext, ProductDescription productDescription)
	        throws BusinessException;

	ProductDescription updateProductDescription(UserContext userContext, ProductDescription productDescription)
	        throws BusinessException;

	ProductDescription removeProductDescription(UserContext userContext, ProductDescription productDescription)
	        throws BusinessException;
}
