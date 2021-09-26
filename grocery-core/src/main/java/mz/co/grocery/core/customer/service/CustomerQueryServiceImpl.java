/**
 *
 */
package mz.co.grocery.core.customer.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.customer.dao.CustomerDAO;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(CustomerQueryServiceImpl.NAME)
public class CustomerQueryServiceImpl implements CustomerQueryService {

	public static final String NAME = "mz.co.grocery.core.customer.service.CustomerQueryServiceImpl";

	@Inject
	private CustomerDAO customerDAO;

	@Override
	public List<Customer> findCustomersByUnit(final String unitUuid, final int currentPage, final int maxResult) throws BusinessException {
		return this.customerDAO.findByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countCustomersByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.countByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithPendingPeymentsByUnit(final String unitUuid, final int currentPage, final int maxResult)
			throws BusinessException {
		return this.customerDAO.findPendingPaymentsByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countCustomersWithPendingPeymentsByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.countPendingPaymentsByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithPendingDevolutionsByUnit(final String unitUuid, final int currentPage, final int maxResult)
			throws BusinessException {
		return this.customerDAO.findPendingDevolutionByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countCustomersWithPendingDevolutionsByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.countPendingDevolutionByUnit(unitUuid, EntityStatus.ACTIVE);
	}
}
