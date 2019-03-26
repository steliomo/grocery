/**
 *
 */
package mz.co.grocery.core.product.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.Product;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ProductDAOImpl.NAME)
public class ProductDAOImpl extends GenericDAOImpl<Product, Long> implements ProductDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ProductDAOImpl";

}
