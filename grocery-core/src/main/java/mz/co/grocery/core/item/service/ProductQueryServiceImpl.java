/**
 *
 */
package mz.co.grocery.core.item.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.dao.ProductDAO;
import mz.co.grocery.core.item.model.Product;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(ProductQueryServiceImpl.NAME)
public class ProductQueryServiceImpl implements ProductQueryService {

	public static final String NAME = "mz.co.grocery.core.item.service.ProductQueryServiceImpl";

	@Inject
	private ProductDAO productDAO;

	@Override
	public List<Product> findAllProducts() throws BusinessException {
		return this.productDAO.findAll(EntityStatus.ACTIVE);
	}

	@Override
	public List<Product> findProductByName(final String name) throws BusinessException {
		return this.productDAO.findByName(name, EntityStatus.ACTIVE);
	}

	@Override
	public Product findProductByUuid(final String uuid) throws BusinessException {
		return this.productDAO.findByUuid(uuid);
	}

	@Override
	public List<Product> findProductsByGrocery(final Grocery grocery) throws BusinessException {
		return this.productDAO.findByGrocery(grocery, EntityStatus.ACTIVE);
	}

	@Override
	public List<Product> findProductsNotInThisGrocery(final Grocery grocery) throws BusinessException {
		return this.productDAO.findNotInThisGrocery(grocery, EntityStatus.ACTIVE);
	}
}
