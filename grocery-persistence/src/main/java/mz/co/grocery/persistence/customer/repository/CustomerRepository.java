/**
 *
 */
package mz.co.grocery.persistence.customer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface CustomerRepository extends GenericDAO<CustomerEntity, Long> {

	class QUERY {

		public static final String countByUnit = "SELECT COUNT(c) FROM CustomerEntity c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus";

		public static final String findByUnit = "SELECT c FROM CustomerEntity c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus";

		public static final String findRentPendingPaymentsByUnit = "SELECT c FROM RentEntity r INNER JOIN r.customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE', 'OVER_PAYMENT') GROUP BY c.id ORDER BY c.name";

		public static final String countPendingPaymentsByUnit = "SELECT COUNT(DISTINCT c) FROM RentEntity r INNER JOIN r.customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE', 'OVER_PAYMENT')";

		public static final String findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "SELECT c FROM RentEntity r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE c.unit.uuid = :unitUuid AND ri.returnStatus IN ('PENDING', 'INCOMPLETE') AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY c.name";

		public static final String countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "SELECT COUNT(DISTINCT c) FROM RentEntity r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE c.unit.uuid = :unitUuid AND ri.stockable = TRUE AND ri.returnStatus = 'PENDING' AND c.entityStatus = :entityStatus";

		public static final String findWithContractPendingPaymentByUnit = "SELECT c FROM ContractEntity ct INNER JOIN ct.customer c WHERE ct.unit.uuid = :unitUuid AND ct.referencePaymentDate <= :currentDate AND ct.entityStatus = :entityStatus GROUP BY c.id ORDER BY c.name";

		public static final String countCustomersWithContractPendingPaymentByUnit = "SELECT COUNT(DISTINCT c) FROM ContractEntity ct INNER JOIN ct.customer c WHERE ct.unit.uuid = :unitUuid AND ct.referencePaymentDate <= :currentDate AND ct.entityStatus = :entityStatus";

		public static final String findCustomersSaleWithPendindOrIncompletePaymentByUnit = "SELECT c FROM SaleEntity s INNER JOIN s.customer c WHERE s.unit.uuid = :unitUuid AND s.total > s.totalPaid AND s.entityStatus = :entityStatus GROUP BY c.id ORDER BY s.saleDate DESC";

		public static final String findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit = "SELECT c FROM RentEntity r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE r.unit.uuid = :unitUuid AND ri.loadStatus IN ('PENDING','INCOMPLETE') AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY r.rentDate DESC";

		public static final String findWithIssuedGuidesByTypeAndUnit = "SELECT c FROM GuideEntity g INNER JOIN g.rent r INNER JOIN r.customer c WHERE r.unit.uuid = :unitUuid AND g.type = :guideType AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY g.issueDate DESC";

		public static final String findCustomersWithPaymentsByUnit = "SELECT c FROM RentEntity r INNER JOIN r.customer c INNER JOIN r.rentPayments ri WHERE r.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.entityStatus = :entityStatus AND ri.entityStatus = :entityStatus GROUP BY c.id ORDER BY r.rentDate DESC";

		public static final String findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit = "SELECT c FROM SaleEntity s INNER JOIN s.customer c WHERE s.unit.uuid = :unitUuid AND s.deliveryStatus IN ('PENDING','INCOMPLETE') AND s.saleType = 'INSTALLMENT' AND c.entityStatus = :entityStatus AND s.entityStatus = :entityStatus GROUP BY c.id ORDER BY s.saleDate DESC";

		public static final String findCustomersWithDeliveredGuidesByUnit = "SELECT c FROM GuideEntity g INNER JOIN g.sale s INNER JOIN s.customer c WHERE s.unit.uuid = :unitUuid AND g.type = 'DELIVERY' AND s.saleType = 'INSTALLMENT' AND c.entityStatus = :entityStatus AND g.entityStatus = :entityStatus GROUP BY c.id ORDER BY g.issueDate DESC";

		public static final String findByUnitAndQuotationType = "SELECT c FROM QuotationEntity q INNER JOIN q.customer c WHERE q.unit.uuid = :unitUuid AND q.type = :type AND q.entityStatus = :entityStatus AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY q.issueDate DESC";

		public static final String findCustomerByContact = "SELECT c FROM CustomerEntity c WHERE c.contact = :contact AND c.entityStatus = :entityStatus";
	}

	class QUERY_NAME {

		public static final String countByUnit = "CustomerEntity.countByUnit";

		public static final String findByUnit = "CustomerEntity.findByUnit";

		public static final String findRentPendingPaymentsByUnit = "CustomerEntity.findRentPendingPaymentsByUnit";

		public static final String countPendingPaymentsByUnit = "CustomerEntity.countPendingPaymentsByUnit";

		public static final String findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "CustomerEntity.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit";

		public static final String countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit = "CustomerEntity.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit";

		public static final String findWithContractPendingPaymentByUnit = "CustomerEntity.findWithContractPendingPaymentByUnit";

		public static final String countCustomersWithContractPendingPaymentByUnit = "CustomerEntity.countCustomersWithContractPendingPaymentByUnit";

		public static final String findCustomersSaleWithPendindOrIncompletePaymentByUnit = "CustomerEntity.findCustomersSaleWithPendindOrIncompletePaymentByUnit";

		public static final String findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit = "CustomerEntity.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit";

		public static final String findWithIssuedGuidesByTypeAndUnit = "CustomerEntity.findWithIssuedGuidesByTypeAndUnit";

		public static final String findCustomersWithPaymentsByUnit = "CustomerEntity.findCustomersWithPaymentsByUnit";

		public static final String findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit = "CustomerEntity.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit";

		public static final String findCustomersWithDeliveredGuidesByUnit = "CustomerEntity.findCustomersWithDeliveredGuidesByUnit";

		public static final String findByUnitAndQuotationType = "CustomerEntity.findByUnitAndQuotationType";

		public static final String findCustomerByContact = "CustomerEntity.findCustomerByContact";
	}

	Long countByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findRentPendingPaymentsByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus)
			throws BusinessException;

	Long countPendingPaymentsByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, int currentPage, int maxResult,
			EntityStatus entityStatus) throws BusinessException;

	Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findWithContractPendingPaymentByUnit(String unitUuid, int currentPage, int maxResult, LocalDate currentDate,
			EntityStatus entityStatus) throws BusinessException;

	Long countCustomersWithContractPendingPaymentByUnit(String unitUuid, LocalDate currentDate, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findCustomersSaleWithPendindOrIncompletePaymentByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(String unitUuid, EntityStatus entityStatus)
			throws BusinessException;

	List<CustomerEntity> findWithIssuedGuidesByTypeAndUnit(GuideType guideType, String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findWithPaymentsByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(String unitUuid, EntityStatus entityStatus)
			throws BusinessException;

	List<CustomerEntity> findCustomersWithDeliveredGuidesByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<CustomerEntity> findByUnitAndQuotationType(String unitUuid, EntityStatus entityStatus, QuotationType type) throws BusinessException;

	Optional<CustomerEntity> findCustomerByContact(String contact, EntityStatus entityStatus) throws BusinessException;
}
