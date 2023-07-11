/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface StockRepositoty extends GenericDAO<StockEntity, Long> {

	class QUERY {

		public static final String findAllIds = "SELECT s.id FROM StockEntity s WHERE s.entityStatus = :entityStatus";

		public static final String fetchAll = "SELECT s FROM StockEntity s INNER JOIN FETCH s.unit INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE s.id IN (:stockIds) GROUP BY s.id ORDER BY pd.product.name, pd.description";

		public static final String fetchByUuid = "SELECT s FROM StockEntity s INNER JOIN FETCH s.unit INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product INNER JOIN FETCH pd.productUnit WHERE s.uuid = :stockUuid AND s.entityStatus = :entityStatus";

		public static final String fetchByProductDescription = "SELECT s FROM StockEntity s INNER JOIN FETCH s.unit INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE CONCAT(p.name, ' ', pd.description) LIKE :description AND s.entityStatus = :entityStatus";

		public static final String fetchByGroceryAndProduct = "SELECT s FROM StockEntity s INNER JOIN FETCH s.unit g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE g.uuid = :groceryUuid AND p.uuid = :productUuid AND s.entityStatus = :entityStatus ORDER BY p.name, pd.description";

		public static final String fetchByGrocery = "SELECT s FROM StockEntity s INNER JOIN FETCH s.unit g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE g.uuid = :groceryUuid AND s.entityStatus = :entityStatus ORDER BY p.name, pd.description";

		public static final String fetchByGroceryAndSalePeriod = "SELECT s FROM SaleItemEntity si INNER JOIN si.stock s INNER JOIN si.sale sa INNER JOIN s.unit g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit "
				+ "WHERE g.uuid = :groceryUuid AND s.entityStatus = :entityStatus and s.stockStatus = 'LOW' AND sa.saleDate BETWEEN :startDate AND :endDate GROUP BY s.id ORDER BY SUM((s.salePrice - s.purchasePrice) * si.quantity - si.discount) DESC";

		public static final String fetchNotInThisGroceryByProduct = "SELECT s FROM StockEntity s INNER JOIN FETCH s.unit g INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE NOT EXISTS (SELECT st FROM StockEntity st INNER JOIN st.productDescription pdn WHERE st.unit.uuid = :groceryUuid AND pdn.id = pd.id) AND p.uuid = :productUuid AND s.entityStatus = :entityStatus GROUP BY pd.id ORDER BY p.name, pd.description";

		public static final String fetchInAnalysisByUnitUuid = "SELECT s FROM StockEntity s INNER JOIN FETCH s.productDescription pd INNER JOIN FETCH pd.product p INNER JOIN FETCH pd.productUnit WHERE s.unit.uuid = :unitUuid AND s.productStockStatus = 'BAD' AND s.entityStatus = :entityStatus ORDER BY CONCAT(p.name,' ', pd.description) ASC";
	}

	class QUERY_NAME {

		public static final String findAllIds = "StockEntity.findAllIds";

		public static final String fetchAll = "StockEntity.fetchAll";

		public static final String fetchByUuid = "StockEntity.fetchByUuid";

		public static final String fetchByProductDescription = "StockEntity.fetchByProductDescription";

		public static final String fetchByGroceryAndProduct = "StockEntity.fetchByGroceryAndProduct";

		public static final String fetchByGrocery = "StockEntity.fetchByGrocery";

		public static final String fetchByGroceryAndSalePeriod = "StockEntity.fetchByGroceryAndSalePeriod";

		public static final String fetchNotInThisGroceryByProduct = "StockEntity.fetchNotInThisGroceryByProduct";

		public static final String fetchInAnalysisByUnitUuid = "StockEntity.fetchInAnalysisByUnitUuid";
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

	List<Stock> fetchInAnalysisByUnitUuid(String unitUuid, EntityStatus entityStatus) throws BusinessException;
}
