/**
 *
 */
package mz.co.grocery.core.product.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.ProductSize;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ProductSizeDAOImpl.NAME)
public class ProductSizeDAOImpl extends GenericDAOImpl<ProductSize, Long> implements ProductSizeDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ProductSizeDAOImpl";

}
