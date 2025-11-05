/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.sale.entity.SalePaymentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class SalePaymentRepositoryImpl extends GenericDAOImpl<SalePaymentEntity, Long> implements SalePaymentRepository {

	@Override
	public Optional<BigDecimal> findDebtCollectionsByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {

		return Optional.ofNullable(this.findSingleByNamedQuery(SalePaymentRepository.QUERY_NAME.findDebtCollectionsByUnitAndPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				BigDecimal.class));
	}

}
