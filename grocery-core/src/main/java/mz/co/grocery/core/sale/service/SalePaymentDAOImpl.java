/**
 *
 */
package mz.co.grocery.core.sale.service;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.sale.model.SalePayment;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */
@Repository(SalePaymentDAOImpl.NAME)
public class SalePaymentDAOImpl extends GenericDAOImpl<SalePayment, Long> implements SalePaymentDAO {

	public static final String NAME = "mz.co.grocery.core.sale.service.SalePaymentDAOImpl";

}
