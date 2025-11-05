/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.grocery.core.domain.sale.SaleItemReport;
import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository
public class SaleItemRepositoryImpl extends GenericDAOImpl<SaleItemEntity, Long> implements SaleItemRepository {

	@Override
	public Optional<SaleItemEntity> findBySaleAndProductUuid(final String saleUuid, final String productUuid, final EntityStatus entityStatus)
			throws BusinessException {
		try {
			return Optional.of(this.findSingleByNamedQuery(SaleItemRepository.QUERY_NAME.findBySaleAndProductUuid,
					new ParamBuilder().add("saleUuid", saleUuid).add("productUuid", productUuid).add("entityStatus", entityStatus)
					.process()));
		} catch (final NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<SaleItemEntity> findBySaleAndServiceUuid(final String saleUuid, final String serviceUuid, final EntityStatus entityStatus)
			throws BusinessException {
		try {
			return Optional.of(this.findSingleByNamedQuery(SaleItemRepository.QUERY_NAME.findBySaleAndServiceUuid,
					new ParamBuilder().add("saleUuid", saleUuid).add("serviceUuid", serviceUuid).add("entityStatus", entityStatus)
					.process()));
		} catch (final NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<SaleItemReport> findSaleItemsByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByNamedQuery(SaleItemRepository.QUERY_NAME.findSaleItemsByUnitAndPeriod,
				new ParamBuilder().add("unitUuid", unitUuid)
				.add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				SaleItemReport.class);
	}

	@Override
	public List<DebtItem> findDeptItemsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleItemRepository.QUERY_NAME.findDeptItemsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), DebtItem.class);
	}
}
