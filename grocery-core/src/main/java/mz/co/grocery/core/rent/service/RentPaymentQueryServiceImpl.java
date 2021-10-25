/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */

@Service(RentPaymentQueryServiceImpl.NAME)
public class RentPaymentQueryServiceImpl implements RentPaymentQueryService {

	public static final String NAME = "mz.co.grocery.core.rent.service.RentPaymentQueryServiceImpl";

	@Inject
	private RentPaymentDAO rentPaymentDAO;

	@Override
	public List<SaleReport> findSalesByUnitAndPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate)
			throws BusinessException {
		return this.rentPaymentDAO.findSalesByUnitAndPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<SaleReport> findSalesByUnitAndMonthlyPeriod(final String unitUuid, final LocalDate startDate, final LocalDate endDate) throws BusinessException {
		return this.rentPaymentDAO.findSalesByUnitAndMonthlyPeriod(unitUuid, startDate, endDate, EntityStatus.ACTIVE);
	}
}
