/**
 *
 */
package mz.co.grocery.core.stock.dao;

import java.util.List;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface StockDAO extends GenericDAO<Stock, Long> {

	class QUERY {

		public static final String findAllIds = "SELECT s.id FROM Stock s WHERE s.entityStatus = :entityStatus";

		public static final String fetchAll = "SELECT s FROM Stock s INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE s.id IN (:stockIds) GROUP BY s.id ORDER BY pd.product.name, pd.description";

		public static final String fetchByUuid = "SELECT s FROM Stock s INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE s.uuid = :stockUuid AND s.entityStatus = :entityStatus";

		public static final String fetchByProductDescription = "SELECT s FROM Stock s INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE pd.description LIKE :description AND s.entityStatus = :entityStatus";

		public static final String fetchByProductUuid = "SELECT s FROM Stock s INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE p.uuid = :productUuid AND s.entityStatus = :entityStatus ORDER BY p.name, pd.description";
	}

	class QUERY_NAME {

		public static final String findAllIds = "Stock.findAllIds";

		public static final String fetchAll = "Stock.fetchAll";

		public static final String fetchByUuid = "Stock.fetchByUuid";

		public static final String fetchByProductDescription = "Stock.fetchByProductDescription";

		public static final String fetchByProductUuid = "Stock.fetchByProductUUid";
	}

	List<Stock> fetchAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	Stock fetchByUuid(String stockUuid, EntityStatus entityStatus) throws BusinessException;

	List<Stock> fetchByProductDescription(String description, EntityStatus entityStatus) throws BusinessException;

	List<Stock> fetchByProductUUid(String productUuid, EntityStatus entityStatus) throws BusinessException;

}
