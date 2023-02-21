/**
 *
 */
package mz.co.grocery.core.sale.dao;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(SaleDAOImpl.NAME)
public class SaleDAOImpl extends GenericDAOImpl<Sale, Long> implements SaleDAO {

	public static final String NAME = "mz.co.grocery.core.sale.dao.SaleDAOImpl";

	@Override
	public List<SaleReport> findPerPeriod(final String groceryUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleDAO.QUERY_NAME.findPerPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process(),
				SaleReport.class);
	}

	@Override
	public List<SaleReport> findMonthlyPerPeriod(final String groceryUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleDAO.QUERY_NAME.findMonthlyPerPeriod,
				new ParamBuilder().add("groceryUuid", groceryUuid).add("startDate", startDate).add("endDate", endDate)
				.add("entityStatus", entityStatus).process(),
				SaleReport.class);
	}

	@Override
	public List<Sale> findPendingOrImpletePaymentSaleStatusByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(SaleDAO.QUERY_NAME.findPendingOrImpletePaymentSaleStatusByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Sale> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(SaleDAO.QUERY_NAME.fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public Sale fetchByUuid(final String uuid) throws BusinessException {
		return this.findSingleByNamedQuery(SaleDAO.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("uuid", uuid).add("entityStatus", EntityStatus.ACTIVE).process());
	}

	@Override
	public List<Sale> fetchSalesWithDeliveryGuidesByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(SaleDAO.QUERY_NAME.fetchSalesWithDeliveryGuidesByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}
}
