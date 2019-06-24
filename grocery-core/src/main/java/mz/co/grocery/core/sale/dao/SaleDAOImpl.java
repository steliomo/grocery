/**
 *
 */
package mz.co.grocery.core.sale.dao;

import java.time.LocalDate;
import java.util.List;

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
	public List<SaleReport> findLast7DaysSale(final EntityStatus entityStatus) throws BusinessException {
		return this
		        .findByQuery(SaleDAO.QUERY_NAME.findLast7DaysSale,
		                new ParamBuilder().add("entityStatus", entityStatus).process(), SaleReport.class)
		        .setMaxResults(7).getResultList();
	}

	@Override
	public List<SaleReport> findPerPeriod(final LocalDate startDate, final LocalDate endDate,
	        final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(SaleDAO.QUERY_NAME.findPerPeriod, new ParamBuilder().add("startDate", startDate)
		        .add("endDate", endDate).add("entityStatus", entityStatus).process(), SaleReport.class);
	}
}
