/**
 *
 */
package mz.co.grocery.core.sale.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.sale.dao.SaleDAO;
import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(SaleQueryServiceImpl.NAME)
public class SaleQueryServiceImpl implements SaleQueryService {

	public static final String NAME = "mz.co.grocery.core.sale.service.SaleQueryServiceImpl";

	@Inject
	private SaleDAO saleDAO;

	@Override
	public List<SaleReport> findLast7DaysSale(final String groceryUuid) throws BusinessException {
		return this.saleDAO.findLast7DaysSale(groceryUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<SaleReport> findSalesPerPeriod(final String groceryUuid, final LocalDate startDate,
	        final LocalDate endDate) throws BusinessException {
		return this.saleDAO.findPerPeriod(groceryUuid, startDate, endDate, EntityStatus.ACTIVE);
	}
}
