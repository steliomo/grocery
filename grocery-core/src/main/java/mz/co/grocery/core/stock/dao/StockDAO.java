/**
 *
 */
package mz.co.grocery.core.stock.dao;

import java.time.LocalDate;
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

		public static final String fetchAll = "SELECT s FROM Stock s INNER JOIN FETCH s.grocery INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE s.id IN (:stockIds) GROUP BY s.id ORDER BY pd.product.name, pd.description";

		public static final String fetchByUuid = "SELECT s FROM Stock s INNER JOIN FETCH s.grocery INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE s.uuid = :stockUuid AND s.entityStatus = :entityStatus";

		public static final String fetchByProductDescription = "SELECT s FROM Stock s INNER JOIN FETCH s.grocery INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE pd.description LIKE :description AND s.entityStatus = :entityStatus";

		public static final String fetchByGroceryAndProduct = "SELECT s FROM Stock s INNER JOIN FETCH s.grocery g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE g.uuid = :groceryUuid AND p.uuid = :productUuid AND s.entityStatus = :entityStatus ORDER BY p.name, pd.description";

		public static final String fetchByGrocery = "SELECT s FROM Stock s INNER JOIN FETCH s.grocery g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE g.uuid = :groceryUuid AND s.entityStatus = :entityStatus ORDER BY p.name, pd.description";

		public static final String fetchByGroceryAndSalePeriod = "SELECT s FROM SaleItem si INNER JOIN si.stock s INNER JOIN si.sale sa INNER JOIN s.grocery g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit "
				+ "WHERE g.uuid = :groceryUuid AND s.entityStatus = :entityStatus and s.stockStatus = 'LOW' AND sa.saleDate BETWEEN :startDate AND :endDate GROUP BY s.id ORDER BY SUM((s.salePrice - s.purchasePrice) * si.quantity - si.discount) DESC";

		public static final String fetchNotInThisGroceryByProduct = "SELECT s FROM Stock s INNER JOIN FETCH s.grocery g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE NOT EXISTS (SELECT st FROM Stock st INNER JOIN st.productDescription pdn WHERE st.grocery.uuid = :groceryUuid AND pdn.id = pd.id) AND p.uuid = :productUuid AND s.entityStatus = :entityStatus GROUP BY pd.id ORDER BY p.name, pd.description";
	}

	class QUERY_NAME {

		public static final String findAllIds = "Stock.findAllIds";

		public static final String fetchAll = "Stock.fetchAll";

		public static final String fetchByUuid = "Stock.fetchByUuid";

		public static final String fetchByProductDescription = "Stock.fetchByProductDescription";

		public static final String fetchByGroceryAndProduct = "Stock.fetchByGroceryAndProduct";

		public static final String fetchByGrocery = "Stock.fetchByGrocery";

		public static final String fetchByGroceryAndSalePeriod = "Stock.fetchByGroceryAndSalePeriod";

		public static final String fetchNotInThisGroceryByProduct = "Stock.fetchNotInThisGroceryByProduct";
	}

	List<Stock> fetchAll(int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	Stock fetchByUuid(String stockUuid, EntityStatus entityStatus) throws BusinessException;

	List<Stock> fetchByProductDescription(String description, EntityStatus entityStatus) throws BusinessException;

	List<Stock> fetchByGroceryAndProduct(String groceryUuid, String productUuid, EntityStatus entityStatus)
			throws BusinessException;

	List<Stock> fetchByGrocery(String groceryUuid, EntityStatus entityStatus) throws BusinessException;

	List<Stock> fetchByGroceryAndSalePeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			EntityStatus entityStatus)
					throws BusinessException;

	List<Stock> fetchNotInThisGroceryByProduct(String groceryUuid, String productUuid, EntityStatus entityStatus)
			throws BusinessException;
}
