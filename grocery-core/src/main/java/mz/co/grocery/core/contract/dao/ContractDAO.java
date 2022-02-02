/**
 *
 */
package mz.co.grocery.core.contract.dao;

import java.time.LocalDate;
import java.util.List;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.msaude.boot.frameworks.dao.GenericDAO;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public interface ContractDAO extends GenericDAO<Contract, Long> {

	class QUERY {
		public static final String findPendingContractsForPaymentByCustomer = "SELECT c FROM Contract c INNER JOIN FETCH c.customer INNER JOIN FETCH c.unit WHERE c.customer.uuid = :customerUuid AND c.referencePaymentDate <= :currentDate AND c.entityStatus = :entityStatus ORDER BY c.referencePaymentDate DESC";
	}

	class QUERY_NAME {
		public static final String findPendingContractsForPaymentByCustomer = "Contract.findPendingContractsForPaymentByCustomer";
	}

	List<Contract> findPendingContractsForPaymentByCustomerUuid(String customerUuid, LocalDate currentDate, EntityStatus entityStatus)
			throws BusinessException;

}
