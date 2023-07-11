/**
 *
 */
package mz.co.grocery.core.application.quotation.out;

import java.util.List;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface FindCustomersPort {

	List<Customer> findCustomersWithPendingQuotation() throws BusinessException;
}
