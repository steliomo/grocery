/**
 *
 */
package mz.co.grocery.core.item.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.item.dao.ProductDAO;
import mz.co.grocery.core.item.model.Product;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductServiceImpl.NAME)
public class ProductServiceImpl extends AbstractService implements ProductService {

	public static final String NAME = "mz.co.grocery.core.item.service.ProductServiceImpl";

	@Inject
	private ProductDAO productDAO;

	@Override
	public Product createProduct(final UserContext userContext, final Product product) throws BusinessException {
		this.productDAO.create(userContext, product);
		return product;
	}

	@Override
	public Product uppdateProduct(final UserContext userContext, final Product product) throws BusinessException {
		this.productDAO.update(userContext, product);
		return product;
	}

	@Override
	public Product removeProduct(final UserContext userContext, final Product product) throws BusinessException {
		product.inactive();
		this.productDAO.update(userContext, product);

		return product;
	}
}
