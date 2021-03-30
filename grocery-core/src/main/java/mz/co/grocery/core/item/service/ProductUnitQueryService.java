/**
 *
 */
package mz.co.grocery.core.item.service;

import java.util.List;

import mz.co.grocery.core.item.model.ProductUnit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductUnitQueryService {

	List<ProductUnit> findAllProductUnits() throws BusinessException;

	ProductUnit findProductUnitByUuid(String productUnitUuid) throws BusinessException;

}
