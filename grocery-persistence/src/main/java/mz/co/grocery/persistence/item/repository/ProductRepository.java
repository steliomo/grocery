/**
 *
 */
package mz.co.grocery.persistence.item.repository;

import java.util.List;

import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.item.entity.ProductEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ProductRepository extends GenericDAO<ProductEntity, Long> {

	class QUERY {
		public static final String findAll = "SELECT p FROM ProductEntity p WHERE p.entityStatus = :entityStatus ORDER BY p.name";
		public static final String findByName = "SELECT p FROM ProductEntity p WHERE p.name LiKE :name AND p.entityStatus = :entityStatus";
		public static final String findByGrocery = "SELECT p FROM StockEntity s INNER JOIN s.productDescription pd INNER JOIN pd.product p WHERE s.unit.uuid = :groceryUuid AND p.entityStatus = :entityStatus AND s.entityStatus = :entityStatus GROUP BY p.id ORDER BY p.name";
		public static final String findNotInThisGrocery = "SELECT p FROM StockEntity s INNER JOIN s.productDescription pd INNER JOIN pd.product p WHERE NOT EXISTS( SELECT pr FROM StockEntity st INNER JOIN st.productDescription pdn INNER JOIN pdn.product pr WHERE st.unit.uuid = :groceryUuid AND pd.id = pdn.id) AND p.entityStatus = :entityStatus AND s.entityStatus = :entityStatus GROUP BY p.id ORDER BY p.name";
	}

	class QUERY_NAME {
		public static final String findAll = "ProductEntity.findAll";
		public static final String findByName = "ProductEntity.findByName";
		public static final String findByGrocery = "ProductEntity.findByGrocery";
		public static final String findNotInThisGrocery = "ProductEntity.findNotInThisGrocery";
	}

	List<Product> findAll(EntityStatus entityStatus) throws BusinessException;

	List<Product> findByName(String name, EntityStatus entityStatus) throws BusinessException;

	List<Product> findByGrocery(Unit grocery, EntityStatus entityStatus) throws BusinessException;

	List<Product> findNotInThisGrocery(Unit grocery, EntityStatus entityStatus) throws BusinessException;
}
