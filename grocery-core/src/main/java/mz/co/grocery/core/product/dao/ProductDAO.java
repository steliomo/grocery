/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.product.model.Product;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductDAO extends GenericDAO<Product, Long> {

	class QUERY {
		public static final String findAll = "SELECT p FROM Product p WHERE p.entityStatus = :entityStatus ORDER BY p.name";
		public static final String findByName = "SELECT p FROM Product p WHERE p.name LiKE :name AND p.entityStatus = :entityStatus";
		public static final String findByGrocery = "SELECT p FROM Stock s INNER JOIN s.productDescription pd INNER JOIN pd.product p WHERE s.grocery.uuid = :groceryUuid AND p.entityStatus = :entityStatus AND s.entityStatus = :entityStatus GROUP BY p.id ORDER BY p.name";
	}

	class QUERY_NAME {
		public static final String findAll = "Product.findAll";
		public static final String findByName = "Product.findByName";
		public static final String findByGrocery = "Product.findByGrocery";
	}

	List<Product> findAll(EntityStatus entityStatus) throws BusinessException;

	List<Product> findByName(String name, EntityStatus entityStatus) throws BusinessException;

	List<Product> findByGrocery(Grocery grocery, EntityStatus entityStatus) throws BusinessException;
}
