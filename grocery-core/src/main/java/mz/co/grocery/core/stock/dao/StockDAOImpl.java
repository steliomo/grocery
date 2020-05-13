/**
 *
 */
package mz.co.grocery.core.stock.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(StockDAOImpl.NAME)
public class StockDAOImpl extends GenericDAOImpl<Stock, Long> implements StockDAO {

	public static final String NAME = " mz.co.grocery.core.stock.dao.StockDAOImpl";

	@Override
	public List<Stock> fetchAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {

		final List<Long> stockIds = this
				.findByQuery(StockDAO.QUERY_NAME.findAllIds,
						new ParamBuilder().add("entityStatus", entityStatus).process(), Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (stockIds.isEmpty()) {
			return new ArrayList<>();
		}

		return this.findByNamedQuery(StockDAO.QUERY_NAME.fetchAll,
				new ParamBuilder().add("stockIds", stockIds).process());
	}

	@Override
	public Stock fetchByUuid(final String stockUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(StockDAO.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("stockUuid", stockUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Stock> fetchByProductDescription(final String description, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(StockDAO.QUERY_NAME.fetchByProductDescription, new ParamBuilder()
				.add("description", "%" + description + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Stock> fetchByGroceryAndProduct(final String groceryUuid, final String productUuid,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(StockDAO.QUERY_NAME.fetchByGroceryAndProduct,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("productUuid", productUuid)
				.add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Stock> fetchByGrocery(final String groceryUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(StockDAO.QUERY_NAME.fetchByGrocery,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Stock> fetchByGroceryAndSalePeriod(final String groceryUuid, final LocalDate startDate,
			final LocalDate endDate, final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByNamedQuery(StockDAO.QUERY_NAME.fetchByGroceryAndSalePeriod, new ParamBuilder()
				.add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process());
	}
}
