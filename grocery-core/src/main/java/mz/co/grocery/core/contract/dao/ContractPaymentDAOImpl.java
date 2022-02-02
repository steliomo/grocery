/**
 *
 */
package mz.co.grocery.core.contract.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.contract.model.ContractPayment;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ContractPaymentDAOImpl.NAME)
public class ContractPaymentDAOImpl extends GenericDAOImpl<ContractPayment, Long> implements ContractPaymentDAO {

	public static final String NAME = "mz.co.grocery.core.contract.dao.ContractPaymentDAOImpl";

}
