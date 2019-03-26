/**
 *
 */
package mz.co.grocery.core.product.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ProductDescriptionDAO;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductDescriptionServiceImpl.NAME)
public class ProductDescriptionServiceImpl extends AbstractService implements ProductDescriptionService {

	public static final String NAME = " mz.co.grocery.core.product.service.ProductDescriptionServiceImpl";

	@Inject
	private ProductDescriptionDAO productDescriptionDAO;

	@Override
	public ProductDescription createProductDescription(final UserContext userContext,
	        final ProductDescription productDescription) throws BusinessException {

		this.productDescriptionDAO.create(userContext, productDescription);

		return productDescription;
	}

}
