/**
 *
 */
package mz.co.grocery.core.rent.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository(RentPaymentDAOImpl.NAME)
public class RentPaymentDAOImpl extends GenericDAOImpl<RentPayment, Long> implements RentPaymentDAO {

	public static final String NAME = "mz.co.grocery.core.rent.dao.RentPaymentDAOImpl";

	@Override
	public List<SaleReport> findSalesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate,
			final EntityStatus entityStatus)
					throws BusinessException {

		return this.findByNamedQuery(RentPaymentDAO.QUERY_NAME.findSalesByUnitAndPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				SaleReport.class);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndMonthlyPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(RentPaymentDAO.QUERY_NAME.findSalesByUnitAndMonthlyPeriod,
				new ParamBuilder().add("unitUuid", unitUuid).add("startDate", startDate).add("endDate", endDate).add("entityStatus", entityStatus)
				.process(),
				SaleReport.class);
	}
}
