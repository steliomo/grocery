/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.sale.entity.SaleEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleRepository extends GenericDAO<SaleEntity, Long> {

	class QUERY {
		public static final String findPerPeriod = "SELECT NEW mz.co.grocery.core.domain.sale.SaleReport(s.saleDate, SUM(CASE WHEN(s.totalPaid <> 0) THEN ((s.totalPaid/s.total) * s.billing) ELSE s.billing END), SUM(CASE WHEN(s.totalPaid <> 0) THEN s.totalPaid ELSE s.total END)) FROM SaleEntity s INNER JOIN s.unit g WHERE g.uuid = :groceryUuid AND s.saleDate >= :startDate AND s.saleDate <= :endDate AND s.saleStatus <> 'PENDING' AND s.entityStatus = :entityStatus GROUP BY s.saleDate ORDER BY s.saleDate ASC";
		public static final String findMonthlyPerPeriod = "SELECT NEW mz.co.grocery.core.domain.sale.SaleReport(s.saleDate, SUM(CASE WHEN(s.totalPaid <> 0) THEN ((s.totalPaid/s.total) * s.billing) ELSE s.billing END), SUM(CASE WHEN(s.totalPaid <> 0) THEN s.totalPaid ELSE s.total END)) FROM SaleEntity s INNER JOIN s.unit g WHERE g.uuid = :groceryUuid AND s.saleDate >= :startDate AND s.saleDate <= :endDate AND s.saleStatus <> 'PENDING' AND s.entityStatus = :entityStatus GROUP BY MONTH(s.saleDate) ORDER BY MONTH(s.saleDate) ASC";
		public static final String findPendingOrImpletePaymentSalesByCustomer = "SELECT s FROM SaleEntity s WHERE s.customer.uuid = :customerUuid AND s.totalPaid < s.total AND s.entityStatus = :entityStatus";
		public static final String fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer = "SELECT DISTINCT s FROM SaleEntity s INNER JOIN FETCH s.items si LEFT JOIN FETCH si.stock st LEFT JOIN FETCH st.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH si.serviceItem svi LEFT JOIN FETCH svi.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE s.customer.uuid = :customerUuid AND s.deliveryStatus IN ('PENDING', 'INCOMPLETE') AND s.saleType = 'INSTALLMENT' AND s.entityStatus = :entityStatus ORDER BY s.saleDate DESC";
		public static final String fetchByUuid = "SELECT s FROM SaleEntity s INNER JOIN FETCH s.customer INNER JOIN FETCH s.unit u LEFT JOIN FETCH s.items i LEFT JOIN FETCH i.stock st LEFT JOIN FETCH st.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH i.serviceItem svi LEFT JOIN FETCH svi.serviceDescription sd LEFT JOIN FETCH sd.service WHERE s.uuid = :uuid AND s.entityStatus = :entityStatus AND i.entityStatus = :entityStatus";

		public static final String fetchSalesWithDeliveryGuidesByCustomer = "SELECT DISTINCT s FROM SaleEntity s INNER JOIN FETCH s.guides g INNER JOIN FETCH g.guideItems gi LEFT JOIN FETCH gi.saleItem si LEFT JOIN FETCH si.stock st LEFT JOIN FETCH st.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH si.serviceItem svi LEFT JOIN FETCH svi.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE s.customer.uuid = :customerUuid AND s.entityStatus = :entityStatus AND g.entityStatus = :entityStatus ORDER BY s.saleDate DESC";

		public static final String fetchOpenedTables = "SELECT s FROM SaleEntity s INNER JOIN FETCH s.customer INNER JOIN FETCH s.unit u WHERE u.uuid = :unitUuid AND s.saleStatus IN ('OPENED', 'IN_PROGRESS') AND s.entityStatus = :entityStatus ORDER BY s.id ASC";
	}

	class QUERY_NAME {
		public static final String findPerPeriod = "SaleEntity.findPerPeriod";
		public static final String findMonthlyPerPeriod = "SaleEntity.findMonthlyPerPeriod";
		public static final String findPendingOrImpletePaymentSalesByCustomer = "SaleEntity.findPendingOrImpletePaymentSalesByCustomer";
		public static final String fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer = "Sale.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer";
		public static final String fetchByUuid = "SaleEntity.fetchByUuid";
		public static final String fetchSalesWithDeliveryGuidesByCustomer = "SaleEntity.fetchSalesWithDeliveryGuidesByCustomer";
		public static final String fetchOpenedTables = "SaleEntity.fetchOpenedTables";

	}

	List<SaleReport> findPerPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			EntityStatus entityStatus) throws BusinessException;

	List<SaleReport> findMonthlyPerPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			EntityStatus entityStatus) throws BusinessException;

	List<SaleEntity> findPendingOrImpletePaymentSalesByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<SaleEntity> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(String customerUuid, EntityStatus entityStatus)
			throws BusinessException;

	SaleEntity fetchByUuid(String uuid) throws BusinessException;

	List<SaleEntity> fetchSalesWithDeliveryGuidesByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<SaleEntity> fetchOpenedTables(String unitUuid, EntityStatus entityStatus) throws BusinessException;
}
