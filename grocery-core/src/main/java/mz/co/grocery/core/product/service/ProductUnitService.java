/**
 *
 */
package mz.co.grocery.core.product.service;

import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductUnitService {

	ProductUnit createProductUnit(UserContext userContext, ProductUnit productUnit) throws BusinessException;

	ProductUnit updateProductUnit(UserContext userContext, ProductUnit productUnit) throws BusinessException;

	ProductUnit removeProductUnit(UserContext userContext, ProductUnit productUnit) throws BusinessException;

}
