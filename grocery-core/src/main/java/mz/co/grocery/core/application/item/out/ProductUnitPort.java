/**
 *
 */
package mz.co.grocery.core.application.item.out;

import java.util.List;

import mz.co.grocery.core.domain.item.ProductUnit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductUnitPort {

	ProductUnit createProductUnit(UserContext userContext, ProductUnit productUnit) throws BusinessException;

	ProductUnit updateProductUnit(UserContext userContext, ProductUnit productUnit) throws BusinessException;

	ProductUnit removeProductUnit(UserContext userContext, ProductUnit productUnit) throws BusinessException;
	
	List<ProductUnit> findAllProductUnits() throws BusinessException;

	ProductUnit findProductUnitByUuid(String productUnitUuid) throws BusinessException;

}
