/**
 *
 */
package mz.co.grocery.core.application.item.out;

import java.util.List;

import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductDescriptionPort {

	ProductDescription createProductDescription(UserContext userContext, ProductDescription productDescription)
			throws BusinessException;

	ProductDescription updateProductDescription(UserContext userContext, ProductDescription productDescription)
			throws BusinessException;

	ProductDescription removeProductDescription(UserContext userContext, ProductDescription productDescription)
			throws BusinessException;

	List<ProductDescription> fetchdAllProductDescriptions(int currentPage, int maxResult) throws BusinessException;

	Long countProductDescriptions() throws BusinessException;

	List<ProductDescription> fetchProductDescriptionByDescription(String description) throws BusinessException;

	ProductDescription fetchProductDescriptionByUuid(String productDescriptionUuid) throws BusinessException;
}
