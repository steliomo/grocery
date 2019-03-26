/**
 *
 */
package mz.co.grocery.core.product.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ProductSizeDAO;
import mz.co.grocery.core.product.model.ProductSize;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductSizeServiceImpl.NAME)
public class ProductSizeServiceImpl extends AbstractService implements ProductSizeService {

	public static final String NAME = "mz.co.grocery.core.product.service.ProductSizeServiceImpl";

	@Inject
	private ProductSizeDAO productSizeDAO;

	@Override
	public ProductSize createProductSize(final UserContext userContext, final ProductSize productSize)
	        throws BusinessException {

		this.productSizeDAO.create(userContext, productSize);

		return productSize;
	}

}
