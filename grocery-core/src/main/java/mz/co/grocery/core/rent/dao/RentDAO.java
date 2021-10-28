/**
 *
 */
package mz.co.grocery.core.rent.dao;

import java.util.List;

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
		public static final String findPendinPaymentsByCustomer = "SELECT DISTINCT r FROM Rent r LEFT JOIN FETCH r.rentItems WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND r.paymentStatus IN ('PENDING', 'INCOMPLETE') ORDER BY r.rentDate DESC";

		public static final String fetchPendingDevolutionsByCustomer = "SELECT DISTINCT r FROM Rent r LEFT JOIN FETCH r.rentItems ri LEFT JOIN FETCH ri.returnItems LEFT JOIN FETCH ri.stock s LEFT JOIN FETCH s.grocery LEFT JOIN FETCH s.productDescription pd LEFT JOIN FETCH pd.product LEFT JOIN FETCH pd.productUnit LEFT JOIN FETCH ri.serviceItem si LEFT JOIN FETCH si.serviceDescription sd LEFT JOIN FETCH sd.service "
				+ "WHERE r.customer.uuid = :customerUuid AND r.entityStatus = :entityStatus AND ri.returnable = TRUE AND ri.returnStatus = 'PENDING' ORDER BY r.rentDate DESC";
	}

	class QUERY_NAME {

		public static final String findPendinPaymentsByCustomer = "Rent.findPendinPaymentsByCustomer";
		public static final String fetchPendingDevolutionsByCustomer = "Rent.fetchPendingDevolutionsByCustomer";

	}

	List<Rent> findPendinPaymentsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

	List<Rent> fetchPendingDevolutionsByCustomer(String customerUuid, EntityStatus entityStatus) throws BusinessException;

}