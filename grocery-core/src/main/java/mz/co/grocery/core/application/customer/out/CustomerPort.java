/**
 *
 */
package mz.co.grocery.core.application.customer.out;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface CustomerPort {

	Customer createCustomer(UserContext userContext, Customer customer) throws BusinessException;

	List<Customer> findCustomersByUnit(String unitUuid, int currentPage, int maxResult) throws BusinessException;

	Long countCustomersByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithRentPendingPeymentsByUnit(String unitUuid, int currentPage, int maxResult) throws BusinessException;

	Long countCustomersWithPendingPeymentsByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, int currentPage, int maxResult)
			throws BusinessException;

	Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithContractPendingPaymentByUnit(String unitUuid, int currentPage, int maxResult, LocalDate currentDate)
			throws BusinessException;

	Long countCustomersWithContractPendingPaymentByUnit(String unitUuid, LocalDate currentDate) throws BusinessException;

	List<Customer> findCustomersSaleWithPendindOrIncompletePaymentByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithIssuedGuidesByTypeAndUnit(GuideType guideType, String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithPaymentsByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithDeliveredGuidesByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithQuotationByUnitAndType(String unitUuid, QuotationType quotationType) throws BusinessException;
}
