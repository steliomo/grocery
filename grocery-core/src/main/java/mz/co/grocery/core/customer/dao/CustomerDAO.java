/**
 *
 */
package mz.co.grocery.core.customer.dao;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface CustomerDAO extends GenericDAO<Customer, Long> {

	class QUERY {

		public static final String countByUnit = "SELECT COUNT(c) FROM Customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus";

		public static final String findByUnit = "SELECT c FROM Customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus";

		public static final String findRentPendingPaymentsByUnit = "SELECT c FROM Rent r INNER JOIN r.customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE', 'OVER_PAYMENT') GROUP BY c.id ORDER BY c.name";

		public static final String countPendingPaymentsByUnit = "SELECT COUNT(DISTINCT c) FROM Rent r INNER JOIN r.customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE', 'OVER_PAYMENT')";

		public static final String findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "SELECT c FROM Rent r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE c.unit.uuid = :unitUuid AND ri.returnStatus IN ('PENDING', 'INCOMPLETE') AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY c.name";

		public static final String countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "SELECT COUNT(DISTINCT c) FROM Rent r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE c.unit.uuid = :unitUuid AND ri.stockable = TRUE AND ri.returnStatus = 'PENDING' AND c.entityStatus = :entityStatus";

		public static final String findWithContractPendingPaymentByUnit = "SELECT c FROM Contract ct INNER JOIN ct.customer c WHERE ct.unit.uuid = :unitUuid AND ct.referencePaymentDate <= :currentDate AND ct.entityStatus = :entityStatus GROUP BY c.id ORDER BY c.name";

		public static final String countCustomersWithContractPendingPaymentByUnit = "SELECT COUNT(DISTINCT c) FROM Contract ct INNER JOIN ct.customer c WHERE ct.unit.uuid = :unitUuid AND ct.referencePaymentDate <= :currentDate AND ct.entityStatus = :entityStatus";

		public static final String findCustomersSaleWithPendindOrIncompletePaymentByUnit = "SELECT c FROM Sale s INNER JOIN s.customer c WHERE s.grocery.uuid = :unitUuid AND s.saleStatus IN ('PENDING','INCOMPLETE') AND s.entityStatus = :entityStatus GROUP BY c.id ORDER BY s.saleDate DESC";

		public static final String findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit = "SELECT c FROM Rent r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE r.unit.uuid = :unitUuid AND ri.loadStatus IN ('PENDING','INCOMPLETE') AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY r.rentDate DESC";

		public static final String findWithIssuedGuidesByTypeAndUnit = "SELECT c FROM Guide g INNER JOIN g.rent r INNER JOIN r.customer c WHERE r.unit.uuid = :unitUuid AND g.type = :guideType AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY g.issueDate DESC";

		public static final String findCustomersWithPaymentsByUnit = "SELECT c FROM Rent r INNER JOIN r.customer c INNER JOIN r.rentPayments ri WHERE r.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.entityStatus = :entityStatus AND ri.entityStatus = :entityStatus GROUP BY c.id ORDER BY r.rentDate DESC";
	}

	class QUERY_NAME {

		public static final String countByUnit = "Customer.countByUnit";

		public static final String findByUnit = "Customer.findByUnit";

		public static final String findRentPendingPaymentsByUnit = "Customer.findRentPendingPaymentsByUnit";

		public static final String countPendingPaymentsByUnit = "Customer.countPendingPaymentsByUnit";

		public static final String findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "Customer.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit";

		public static final String countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "Customer.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit";

		public static final String findWithContractPendingPaymentByUnit = "Customer.findWithContractPendingPaymentByUnit";

		public static final String countCustomersWithContractPendingPaymentByUnit = "Customer.countCustomersWithContractPendingPaymentByUnit";

		public static final String findCustomersSaleWithPendindOrIncompletePaymentByUnit = "Customer.findCustomersSaleWithPendindOrIncompletePaymentByUnit";

		public static final String findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit = "Customer.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit";

		public static final String findWithIssuedGuidesByTypeAndUnit = "Customer.findWithIssuedGuidesByTypeAndUnit";

		public static final String findCustomersWithPaymentsByUnit = "Customer.findCustomersWithPaymentsByUnit";
	}

	Long countByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findRentPendingPaymentsByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	Long countPendingPaymentsByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, int currentPage, int maxResult,
			EntityStatus entityStatus) throws BusinessException;

	Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findWithContractPendingPaymentByUnit(String unitUuid, int currentPage, int maxResult, LocalDate currentDate,
			EntityStatus entityStatus) throws BusinessException;

	Long countCustomersWithContractPendingPaymentByUnit(String unitUuid, LocalDate currentDate, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findCustomersSaleWithPendindOrIncompletePaymentByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findWithIssuedGuidesByTypeAndUnit(GuideType guideType, String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findWithPaymentsByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;
}
