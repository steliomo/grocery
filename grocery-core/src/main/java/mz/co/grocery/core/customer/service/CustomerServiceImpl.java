/**
 *
 */
package mz.co.grocery.core.customer.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.customer.dao.CustomerDAO;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@Service(CustomerServiceImpl.NAME)
public class CustomerServiceImpl extends AbstractService implements CustomerService {

	public static final String NAME = "mz.co.grocery.core.customer.service.CustomerServiceImpl";

	@Inject
	private CustomerDAO customerDAO;

	@Override
	public Customer createCustomer(final UserContext userContext, final Customer customer) throws BusinessException {
		this.customerDAO.create(userContext, customer);
		return customer;
	}

}
