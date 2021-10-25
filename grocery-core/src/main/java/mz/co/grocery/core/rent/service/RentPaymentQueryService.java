/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.sale.model.SaleReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface RentPaymentQueryService {

	List<SaleReport> findSalesByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate) throws BusinessException;

	List<SaleReport> findSalesByUnitAndMonthlyPeriod(String unitUuid, LocalDate startDate, LocalDate endDate) throws BusinessException;

}
