/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.sale.entity.SaleEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class SaleRepositoryImpl extends GenericDAOImpl<SaleEntity, Long> implements SaleRepository {

	@Override
	public List<SaleReport> findPerPeriod(final String groceryUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleRepository.QUERY_NAME.findPerPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process(),
				SaleReport.class);
	}

	@Override
	public List<SaleReport> findMonthlyPerPeriod(final String groceryUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleRepository.QUERY_NAME.findMonthlyPerPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process(),
				SaleReport.class);
	}

	@Override
	public List<SaleEntity> findPendingOrImpletePaymentSalesByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(SaleRepository.QUERY_NAME.findPendingOrImpletePaymentSalesByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<SaleEntity> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(SaleRepository.QUERY_NAME.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public SaleEntity fetchByUuid(final String uuid) throws BusinessException {
		return this.findSingleByNamedQuery(SaleRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("uuid", uuid).add("entityStatus", EntityStatus.ACTIVE).process());
	}

	@Override
	public List<SaleEntity> fetchSalesWithDeliveryGuidesByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(SaleRepository.QUERY_NAME.fetchSalesWithDeliveryGuidesByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public List<SaleEntity> fetchOpenedTables(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleRepository.QUERY_NAME.fetchOpenedTables,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<SaleEntity> findCreditSaleTypeAndPendingSaleStatusSalesByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(SaleRepository.QUERY_NAME.findCreditSaleTypeAndPendingSaleStatusSalesByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public Debt findDeptByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(SaleRepository.QUERY_NAME.findDeptByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), Debt.class);
	}

	@Override
	public Optional<BigDecimal> findTotalCashByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {
		return Optional.ofNullable(this.findSingleByNamedQuery(SaleRepository.QUERY_NAME.findTotalCashByUnitAndPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				BigDecimal.class));
	}

	@Override
	public Optional<BigDecimal> findTotalCreditByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {
		return Optional.ofNullable(this.findSingleByNamedQuery(SaleRepository.QUERY_NAME.findTotalCreditByUnitAndPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				BigDecimal.class));
	}
}
