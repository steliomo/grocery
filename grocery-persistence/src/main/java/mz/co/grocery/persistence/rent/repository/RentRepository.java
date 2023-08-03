/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.persistence.rent.entity.RentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface RentRepository extends GenericDAO<RentEntity, Long> {

	class QUERY {
		public static final String findPendinPaymentsByCustomer = "SELECT DISTINCT r FROM RentEntity r LEFT JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.unit LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.unit LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE', 'OVER_PAYMENT') ORDER BY r.rentDate DESC";

		public static final String fetchPendingOrIncompleteRentItemToLoadByCustomer = "SELECT DISTINCT r FROM RentEntity r INNER JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND ri.loadStatus IN ('PENDING', 'INCOMPLETE') ORDER BY r.rentDate DESC";

		public static final String fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer = "SELECT DISTINCT r FROM RentEntity r INNER JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND ri.returnStatus IN ('PENDING', 'INCOMPLETE') ORDER BY r.rentDate DESC";

		public static final String fetchByUuid = "SELECT DISTINCT r FROM RentEntity r INNER JOIN FETCH r.rentItems WHERE r.uuid = :uuid";

		public static final String fetchRentsWithIssuedGuidesByTypeAndCustomer = "SELECT DISTINCT r FROM RentEntity r INNER JOIN FETCH r.guides g INNER JOIN FETCH g.guideItems gi INNER JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE g.type = :guideType AND r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus ORDER BY r.rentDate DESC";

		public static final String fetchRentsWithPaymentsByCustomer = "SELECT DISTINCT r FROM RentEntity r INNER JOIN FETCH r.rentPayments rp WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND rp.entityStatus = :entityStatus ORDER BY r.rentDate DESC";

		public static final String findByCustomerAndUnitAndStatus = "SELECT r FROM RentEntity r WHERE r.customer.uuid = :customerUuid AND r.unit.uuid = :unitUuid AND r.rentStatus = :rentStatus AND r.entityStatus = :entityStatus";
	}

	class QUERY_NAME {
		public static final String findPendinPaymentsByCustomer = "RentEntity.findPendinPaymentsByCustomer";
		public static final String fetchPendingOrIncompleteRentItemToLoadByCustomer = "RentEntity.fetchPendingOrIncompleteRentItemToLoadByCustomer";
		public static final String fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer = "Rent.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer";
		public static final String fetchByUuid = "RentEntity.fetchByUuid";
		public static final String fetchRentsWithIssuedGuidesByTypeAndCustomer = "RentEntity.fetchRentsWithIssuedGuidesByTypeAndCustomer";
		public static final String fetchRentsWithPaymentsByCustomer = "RentEntity.fetchRentsWithPaymentsByCustomer";
		public static final String findByCustomerAndUnitAndStatus = "RentEntity.findByCustomerAndUnitAndStatus";
	}

	List<RentEntity> findPendinPaymentsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<RentEntity> fetchPendingOrIncompleteRentItemToLoadByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<RentEntity> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(String customerUuid, EntityStatus entityStatus)
			throws BusinessException;

	RentEntity fetchByUuid(String uuid) throws BusinessException;

	List<RentEntity> fetchWithIssuedGuidesByTypeAndCustomer(GuideType guideType, String customerUuid, EntityStatus entityStatus)
			throws BusinessException;

	List<RentEntity> fetchWithPaymentsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	Optional<RentEntity> findByCustomerAndUnitAndStatus(String customerUuid, String unitUuid, RentStatus rentStatus, EntityStatus entityStatus)
			throws BusinessException;

}
