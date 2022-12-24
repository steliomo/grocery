/**
 *
 */
package mz.co.grocery.core.sale.dao;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleDAO extends GenericDAO<Sale, Long> {

	class QUERY {
		public static final String findPerPeriod = "SELECT NEW mz.co.grocery.core.sale.model.SaleReport(s.saleDate, SUM(CASE WHEN(s.totalPaid <> 0) THEN ((s.totalPaid/s.total) * s.billing) ELSE s.billing END), SUM(CASE WHEN(s.totalPaid <> 0) THEN s.totalPaid ELSE s.total END)) FROM Sale s INNER JOIN s.grocery g WHERE g.uuid = :groceryUuid AND s.saleDate >= :startDate AND s.saleDate <= :endDate AND s.saleStatus <> 'PENDING' AND s.entityStatus = :entityStatus GROUP BY s.saleDate ORDER BY s.saleDate ASC";
		public static final String findMonthlyPerPeriod = "SELECT NEW mz.co.grocery.core.sale.model.SaleReport(s.saleDate, SUM(CASE WHEN(s.totalPaid <> 0) THEN ((s.totalPaid/s.total) * s.billing) ELSE s.billing END), SUM(CASE WHEN(s.totalPaid <> 0) THEN s.totalPaid ELSE s.total END)) FROM Sale s INNER JOIN s.grocery g WHERE g.uuid = :groceryUuid AND s.saleDate >= :startDate AND s.saleDate <= :endDate AND s.saleStatus <> 'PENDING' AND s.entityStatus = :entityStatus GROUP BY MONTH(s.saleDate) ORDER BY MONTH(s.saleDate) ASC";
		public static final String findPendingOrImpletePaymentSaleStatusByCustomer = "SELECT s FROM Sale s WHERE s.customer.uuid = :customerUuid AND s.saleStatus IN ('PENDING', 'INCOMPLETE') AND s.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findPerPeriod = "Sale.findPerPeriod";
		public static final String findMonthlyPerPeriod = "Sale.findMonthlyPerPeriod";
		public static final String findPendingOrImpletePaymentSaleStatusByCustomer = "Sale.findPendingOrImpletePaymentSaleStatusByCustomer";

	}

	List<SaleReport> findPerPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			EntityStatus entityStatus) throws BusinessException;

	List<SaleReport> findMonthlyPerPeriod(String groceryUuid, LocalDate startDate, LocalDate endDate,
			EntityStatus entityStatus) throws BusinessException;

	List<Sale> findPendingOrImpletePaymentSaleStatusByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;
}
