/**
 *
 */
package mz.co.grocery.core.rent.dao;

import java.util.List;

import mz.co.grocery.core.rent.model.GuideType;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface RentDAO extends GenericDAO<Rent, Long> {

	class QUERY {
		public static final String findPendinPaymentsByCustomer = "SELECT DISTINCT r FROM Rent r LEFT JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.grocery LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.unit LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE', 'OVER_PAYMENT') ORDER BY r.rentDate DESC";

		public static final String fetchPendingOrIncompleteRentItemToLoadByCustomer = "SELECT DISTINCT r FROM Rent r INNER JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND ri.loadStatus IN ('PENDING', 'INCOMPLETE') ORDER BY r.rentDate DESC";

		public static final String fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer = "SELECT DISTINCT r FROM Rent r INNER JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND ri.returnStatus IN ('PENDING', 'INCOMPLETE') ORDER BY r.rentDate DESC";

		public static final String fetchByUuid = "SELECT DISTINCT r FROM Rent r INNER JOIN FETCH r.rentItems WHERE r.uuid = :uuid";

		public static final String fetchRentsWithIssuedGuidesByTypeAndCustomer = "SELECT DISTINCT r FROM Rent r INNER JOIN FETCH r.guides g INNER JOIN FETCH g.guideItems gi INNER JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE g.type = :guideType AND r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus ORDER BY r.rentDate DESC";

		public static final String fetchRentsWithPaymentsByCustomer = "SELECT DISTINCT r FROM Rent r INNER JOIN FETCH r.rentPayments rp WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND rp.entityStatus = :entityStatus ORDER BY r.rentDate DESC";
	}

	class QUERY_NAME {
		public static final String findPendinPaymentsByCustomer = "Rent.findPendinPaymentsByCustomer";
		public static final String fetchPendingOrIncompleteRentItemToLoadByCustomer = "Rent.fetchPendingOrIncompleteRentItemToLoadByCustomer";
		public static final String fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer = "Rent.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer";
		public static final String fetchByUuid = "Rent.fetchByUuid";
		public static final String fetchRentsWithIssuedGuidesByTypeAndCustomer = "Rent.fetchRentsWithIssuedGuidesByTypeAndCustomer";
		public static final String fetchRentsWithPaymentsByCustomer = "Rent.fetchRentsWithPaymentsByCustomer";
	}

	List<Rent> findPendinPaymentsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<Rent> fetchPendingOrIncompleteRentItemToLoadByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<Rent> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	Rent fetchByUuid(String uuid) throws BusinessException;

	List<Rent> fetchWithIssuedGuidesByTypeAndCustomer(GuideType guideType, String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<Rent> fetchWithPaymentsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

}
