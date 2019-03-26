/**
 *
 */
package mz.co.grocery.core.product.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.product.dao.ProductDAO;
import mz.co.grocery.core.product.model.Product;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductServiceImpl.NAME)
public class ProductServiceImpl extends AbstractService implements ProductService {

	public static final String NAME = "mz.co.grocery.core.product.service.ProductServiceImpl";

	@Inject
	private ProductDAO productDAO;

	@Override
	public Product createProduct(final UserContext userContext, final Product product) throws BusinessException {
		this.productDAO.create(userContext, product);
		return product;
	}
}
