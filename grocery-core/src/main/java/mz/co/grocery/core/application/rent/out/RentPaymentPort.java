/**
 *
 */
package mz.co.grocery.core.application.rent.out;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RentPaymentPort {

	RentPayment createRentPayment(UserContext userContext, RentPayment rentPayment) throws BusinessException;

	List<SaleReport> findSalesByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate)
			throws BusinessException;

	List<SaleReport> findSalesByUnitAndMonthlyPeriod(String unitUuid, LocalDate startDate, LocalDate endDate)
			throws BusinessException;
}
