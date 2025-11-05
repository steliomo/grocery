/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import mz.co.grocery.persistence.sale.entity.SalePaymentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface SalePaymentRepository extends GenericDAO<SalePaymentEntity, Long> {

	class QUERY {
		public static final String findDebtCollectionsByUnitAndPeriod = "SELECT SUM(sp.paymentValue) FROM SalePaymentEntity sp INNER JOIN sp.sale s INNER JOIN s.unit u WHERE s.saleType = 'CREDIT' AND u.uuid = :unitUuid AND sp.paymentDate BETWEEN :startDate AND :endDate AND s.entityStatus = :entityStatus";

	}

	class QUERY_NAME {
		public static final String findDebtCollectionsByUnitAndPeriod = "SalePaymentEntity.findDebtCollectionsByUnitAndPeriod";

	}

	Optional<BigDecimal> findDebtCollectionsByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate, EntityStatus entityStatus)
			throws BusinessException;
}
