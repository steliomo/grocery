/**
 *
 */
package mz.co.grocery.core.product.service;

import java.util.List;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductDescriptionQueryService {

	List<ProductDescription> fetchdAllProductDescriptions(int currentPage, int maxResult) throws BusinessException;

	Long countProductDescriptions() throws BusinessException;

	List<ProductDescription> fetchProductDescriptionByDescription(String description) throws BusinessException;

	ProductDescription fetchProductDescriptionByUuid(String productDescriptionUuid) throws BusinessException;
}
