/**
 *
 */
package mz.co.grocery.core.application.sale.out;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleReport;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface SalePort {

	Sale createSale(UserContext context, Sale sale) throws BusinessException;

	Sale updateSale(UserContext context, Sale sale) throws BusinessException;

	Sale fetchByUuid(String uuid) throws BusinessException;

	List<SaleReport> findSalesByUnitAndPeriod(String unitUuid, LocalDate startDate, LocalDate endDate) throws BusinessException;

	List<SaleReport> findSalesByUnitAndMonthlyPeriod(String unitUuid, LocalDate startDate, LocalDate endDate) throws BusinessException;

	List<Sale> findPendingOrImpletePaymentSaleStatusByCustomer(Customer customer) throws BusinessException;

	List<Sale> fetchSalesWithPendingOrIncompleteDeliveryStatusByCustomer(Customer customer) throws BusinessException;

	List<Sale> fetchSalesWithDeliveryGuidesByCustomer(Customer customer) throws BusinessException;

	Sale findByUuid(String saleUuid) throws BusinessException;

	List<Sale> fetchOpenedTables(String unitUuid) throws BusinessException;
}
