/**
 *
 */
package mz.co.grocery.core.sale.service;

import java.util.List;

import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface SaleQueryService {
	List<SaleReport> findLast7DaysSale() throws BusinessException;
}
