/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.grocery.persistence.sale.entity.SaleEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class SaleRepositoryImpl extends GenericDAOImpl<SaleEntity, Long> implements SaleRepository {

	private EntityMapper<SaleEntity, Sale> mapper;

	public SaleRepositoryImpl(final EntityMapper<SaleEntity, Sale> mapper) {
		this.mapper = mapper;
	}

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
	public List<Sale> findPendingOrImpletePaymentSaleStatusByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(SaleRepository.QUERY_NAME.findPendingOrImpletePaymentSaleStatusByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process()).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Sale> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(SaleRepository.QUERY_NAME.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList().stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Sale fetchByUuid(final String uuid) throws BusinessException {
		return this.mapper.toDomain(this.findSingleByNamedQuery(SaleRepository.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("uuid", uuid).add("entityStatus", EntityStatus.ACTIVE).process()));
	}

	@Override
	public List<Sale> fetchSalesWithDeliveryGuidesByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(SaleRepository.QUERY_NAME.fetchSalesWithDeliveryGuidesByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList().stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}
}
