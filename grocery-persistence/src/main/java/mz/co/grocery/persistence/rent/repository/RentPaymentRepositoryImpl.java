/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.rent.entity.RentPaymentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class RentPaymentRepositoryImpl extends GenericDAOImpl<RentPaymentEntity, Long> implements RentPaymentRepository {

	@Override
	public List<SaleReport> findSalesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {

		return this.findByNamedQuery(RentPaymentRepository.QUERY_NAME.findSalesByUnitAndPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				SaleReport.class);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndMonthlyPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByNamedQuery(RentPaymentRepository.QUERY_NAME.findSalesByUnitAndMonthlyPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				SaleReport.class);
	}
}
