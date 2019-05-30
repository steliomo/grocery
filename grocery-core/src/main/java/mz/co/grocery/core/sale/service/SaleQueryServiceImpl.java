/**
 *
 */
package mz.co.grocery.core.sale.service;

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
	public List<SaleReport> findLast7DaysSale() throws BusinessException {
		return this.saleDAO.findLast7DaysSale(EntityStatus.ACTIVE);
	}
}
