/**
 *
 */
package mz.co.grocery.core.customer.service;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface CustomerService {

	Customer createCustomer(UserContext userContext, Customer customer) throws BusinessException;

}
