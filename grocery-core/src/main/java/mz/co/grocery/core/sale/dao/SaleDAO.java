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
		public static final String findLast7DaysSale = "SELECT NEW mz.co.grocery.core.sale.model.SaleReport(s.saleDate, SUM(s.profit), SUM(s.total)) FROM Sale s WHERE s.entityStatus = :entityStatus GROUP BY s.saleDate ORDER BY s.saleDate DESC";
		public static final String findPerPeriod = "SELECT NEW mz.co.grocery.core.sale.model.SaleReport(s.saleDate, SUM(s.profit), SUM(s.total)) FROM Sale s WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate AND s.entityStatus = :entityStatus GROUP BY s.saleDate ORDER BY s.saleDate ASC";
	}

	class QUERY_NAME {
		public static final String findLast7DaysSale = "Sale.findLast7DaysSale";
		public static final String findPerPeriod = "Sale.findPerPeriod";
	}

	List<SaleReport> findLast7DaysSale(EntityStatus entityStatus) throws BusinessException;

	List<SaleReport> findPerPeriod(LocalDate startDate, LocalDate endDate, EntityStatus entityStatus)
	        throws BusinessException;
}
