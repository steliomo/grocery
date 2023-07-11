/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.rent.entity.RentPaymentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface RentPaymentRepository extends GenericDAO<RentPaymentEntity, Long> {

	class QUERY {
		public static final String findSalesByUnitAndPeriod = "SELECT NEW mz.co.grocery.core.domain.sale.SaleReport(rp.paymentDate, SUM(rp.paymentValue), SUM(rp.paymentValue)) FROM RentPaymentEntity rp WHERE rp.rent.unit.uuid = :unitUuid AND rp.paymentDate BETWEEN :startDate AND :endDate AND rp.entityStatus =:entityStatus GROUP BY rp.paymentDate ORDER BY rp.paymentDate ASC";

		public static final String findSalesByUnitAndMonthlyPeriod = "SELECT NEW mz.co.grocery.core.domain.sale.SaleReport(rp.paymentDate, SUM(rp.paymentValue), SUM(rp.paymentValue)) FROM RentPaymentEntity rp WHERE rp.rent.unit.uuid = :unitUuid AND rp.paymentDate BETWEEN :startDate AND :endDate AND rp.entityStatus =:entityStatus GROUP BY MONTH(rp.paymentDate) ORDER BY MONTH(rp.paymentDate) ASC";
	}

	class QUERY_NAME {

		public static final String findSalesByUnitAndPeriod = "SaleReport.findSalesByUnitAndPeriod";

		public static final String findSalesByUnitAndMonthlyPeriod = "SaleReport.findSalesByUnitAndMonthlyPeriod";
	}

	List<SaleReport> findSalesByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate, EntityStatus entityStatus)
			throws BusinessException;

	List<SaleReport> findSalesByUnitAndMonthlyPeriod(String unitUuid, LocalDate startDate, LocalDate endDate, EntityStatus entityStatus)
			throws BusinessException;

}
