/**
 *
 */
package mz.co.grocery.core.rent.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository(RentPaymentDAOImpl.NAME)
public class RentPaymentDAOImpl extends GenericDAOImpl<RentPayment, Long> implements RentPaymentDAO {
	public static final String NAME = "mz.co.grocery.core.rent.dao.RentPaymentDAOImpl";
}
