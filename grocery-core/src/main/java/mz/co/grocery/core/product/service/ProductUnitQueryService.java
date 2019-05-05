/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import mz.co.grocery.core.product.model.ProductUnit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductUnitQueryService {

	List<ProductUnit> findAllProductUnits() throws BusinessException;

	ProductUnit findProductUnitByUuid(String productUnitUuid) throws BusinessException;

}
