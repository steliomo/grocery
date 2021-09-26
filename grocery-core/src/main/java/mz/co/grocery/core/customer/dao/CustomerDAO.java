/**
 *
 */
package mz.co.grocery.core.customer.dao;

import java.util.List;

import mz.co.grocery.core.customer.model.Customer;
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

		public static final String findPendingPaymentsByUnit = "SELECT c FROM Rent r INNER JOIN r.customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE') GROUP BY c.id ORDER BY c.name";

		public static final String countPendingPaymentsByUnit = "SELECT COUNT(DISTINCT c) FROM Rent r INNER JOIN r.customer c WHERE c.unit.uuid = :unitUuid AND c.entityStatus = :entityStatus AND r.paymentStatus = 'PENDING'";

		public static final String findPendingDevolutionByUnit = "SELECT c FROM Rent r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE c.unit.uuid = :unitUuid AND ri.returnable = true AND ri.returnStatus = 'PENDING' AND c.entityStatus = :entityStatus GROUP BY c.id ORDER BY c.name";

		public static final String countPendingDevolutionByUnit = "SELECT COUNT(DISTINCT c) FROM Rent r INNER JOIN r.customer c INNER JOIN r.rentItems ri WHERE c.unit.uuid = :unitUuid AND ri.returnable = TRUE AND ri.returnStatus = 'PENDING' AND c.entityStatus = :entityStatus ORDER BY c.name";
	}

	class QUERY_NAME {

		public static final String countByUnit = "Customer.countByUnit";

		public static final String findByUnit = "Customer.findByUnit";

		public static final String findPendingPaymentsByUnit = "Customer.findPendingPaymentsByUnit";

		public static final String countPendingPaymentsByUnit = "Customer.countPendingPaymentsByUnit";

		public static final String findPendingDevolutionByUnit = "Customer.findPendingDevolutionByUnit";

		public static final String countPendingDevolutionByUnit = "Customer.countPendingDevolutionByUnit";

	}

	Long countByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findPendingPaymentsByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	Long countPendingPaymentsByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;

	List<Customer> findPendingDevolutionByUnit(String unitUuid, int currentPage, int maxResult, EntityStatus entityStatus) throws BusinessException;

	Long countPendingDevolutionByUnit(String unitUuid, EntityStatus entityStatus) throws BusinessException;
}
