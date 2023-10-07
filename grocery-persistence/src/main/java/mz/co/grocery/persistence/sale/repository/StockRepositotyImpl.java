/**
 *
 */
package mz.co.grocery.persistence.sale.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.sale.Stock;
import mz.co.grocery.persistence.sale.entity.StockEntity;
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
public class StockRepositotyImpl extends GenericDAOImpl<StockEntity, Long> implements StockRepositoty {

	public StockRepositotyImpl(final EntityMapper<StockEntity, Stock> mapper) {
	}

	@Override
	public List<StockEntity> fetchAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {

		final List<Long> stockIds = this
				.findByQuery(StockRepositoty.QUERY_NAME.findAllIds,
						new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (stockIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchAll,
				new ParamBuilder().add("stockIds", stockIds).process());
	}

	@Override
	public StockEntity fetchByUuid(final String stockUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(StockRepositoty.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("stockUuid", stockUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<StockEntity> fetchByProductDescription(final String description, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchByProductDescription, new ParamBuilder()
				.add("description", "%" + description + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public List<StockEntity> fetchByGroceryAndProduct(final String groceryUuid, final String productUuid,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchByGroceryAndProduct,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("productUuid", productUuid)
				.add("entityStatus", entityStatus).process());
	}

	@Override
	public List<StockEntity> fetchByGrocery(final String groceryUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchByGrocery,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<StockEntity> fetchByGroceryAndSalePeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate, final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchByGroceryAndSalePeriod, new ParamBuilder()
				.add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process());
	}

	@Override
	public List<StockEntity> fetchNotInThisGroceryByProduct(final String groceryUuid, final String productUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchNotInThisGroceryByProduct,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("productUuid", productUuid)
				.add("entityStatus", entityStatus).process());
	}

	@Override
	public List<StockEntity> fetchInAnalysisByUnitUuid(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(StockRepositoty.QUERY_NAME.fetchInAnalysisByUnitUuid,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}
}
