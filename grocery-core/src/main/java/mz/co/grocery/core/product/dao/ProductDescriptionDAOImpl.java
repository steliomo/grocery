/**
 *
 */
package mz.co.grocery.core.product.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ProductDescriptionDAOImpl.NAME)
public class ProductDescriptionDAOImpl extends GenericDAOImpl<ProductDescription, Long>
        implements ProductDescriptionDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ProductDescriptionDAOImpl";

}
