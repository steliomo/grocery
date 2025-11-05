/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.grocery.core.domain.sale.SaleItemReport;
import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleItemRepository extends GenericDAO<SaleItemEntity, Long> {

	class QUERY {
		public static final String findBySaleAndProductUuid = "SELECT si FROM SaleItemEntity si WHERE si.sale.uuid = :saleUuid AND si.stock.uuid = :productUuid AND si.entityStatus = :entityStatus";
		public static final String findBySaleAndServiceUuid = "SELECT si FROM SaleItemEntity si WHERE si.sale.uuid = :saleUuid AND si.serviceItem.uuid = :serviceUuid AND si.entityStatus = :entityStatus";
		public static final String findSaleItemsByUnitAndPeriod = "SELECT NEW mz.co.grocery.core.domain.sale.SaleItemReport(CONCAT(p.name, ' ', pd.description, ' ', pu.unit, ' ', pu.productUnitType), SUM(si.quantity), st.salePrice) FROM SaleItemEntity si INNER JOIN si.sale s INNER JOIN si.stock st INNER JOIN st.productDescription pd "
				+ "INNER JOIN pd.product p INNER JOIN pd.productUnit pu WHERE s.unit.uuid = :unitUuid AND s.saleDate BETWEEN :startDate AND :endDate AND ((s.saleType IN ('INSTALLMENT', 'CASH') AND s.saleStatus = 'CLOSED') OR (s.saleType = 'CREDIT' AND s.saleStatus IN ('PENDING', 'CLOSED'))) AND s.entityStatus = :entityStatus "
				+ "AND si.entityStatus = :entityStatus GROUP BY st.id ORDER BY p.name";
		public static final String findDeptItemsByCustomer = "SELECT NEW mz.co.grocery.core.domain.pos.DebtItem(s.saleDate, CONCAT(p.name, ' ', pd.description, ' ', pu.unit, ' ', pu.productUnitType), si.quantity, st.salePrice, si.saleItemValue) FROM SaleItemEntity si INNER JOIN si.sale s INNER JOIN si.stock st INNER JOIN st.productDescription pd "
				+ "INNER JOIN pd.product p INNER JOIN pd.productUnit pu INNER JOIN s.customer c WHERE s.saleType = 'CREDIT' AND s.saleStatus = 'PENDING' AND c.uuid = :customerUuid AND s.entityStatus = :entityStatus ORDER BY s.saleDate ASC";

	}

	class QUERY_NAME {
		public static final String findBySaleAndProductUuid = "SaleItemEntity.findBySaleAndProductUuid";
		public static final String findBySaleAndServiceUuid = "SaleItemEntity.findBySaleAndServiceUuid";
		public static final String findSaleItemsByUnitAndPeriod = "SaleItemEntity.findSaleItemsByUnitAndPeriod";
		public static final String findDeptItemsByCustomer = "SaleItemEntity.findDeptItemsByCustomer";
	}

	Optional<SaleItemEntity> findBySaleAndProductUuid(String saleUuid, String productUuid, EntityStatus entityStatus) throws BusinessException;

	Optional<SaleItemEntity> findBySaleAndServiceUuid(String saleUuid, String serviceUuid, EntityStatus entityStatus) throws BusinessException;

	List<SaleItemReport> findSaleItemsByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate,
			EntityStatus entityStatus)
					throws BusinessException;

	List<DebtItem> findDeptItemsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;
}
