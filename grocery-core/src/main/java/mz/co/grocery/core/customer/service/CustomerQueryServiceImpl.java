/**
 *
 */
package mz.co.grocery.core.customer.service;

import java.time.LocalDate;
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
	public List<Customer> findCustomersWithRentPendingPeymentsByUnit(final String unitUuid, final int currentPage, final int maxResult)
			throws BusinessException {
		return this.customerDAO.findRentPendingPaymentsByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countCustomersWithPendingPeymentsByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.countPendingPaymentsByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final int currentPage, final int maxResult)
			throws BusinessException {
		return this.customerDAO.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE);
	}

	@Override
	public Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithContractPendingPaymentByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final LocalDate currentDate)
					throws BusinessException {
		return this.customerDAO.findWithContractPendingPaymentByUnit(unitUuid, currentPage, maxResult, currentDate, EntityStatus.ACTIVE);
	}

	@Override
	public Long countCustomersWithContractPendingPaymentByUnit(final String unitUuid, final LocalDate currentDate) throws BusinessException {
		return this.customerDAO.countCustomersWithContractPendingPaymentByUnit(unitUuid, currentDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersSaleWithPendindOrIncompletePaymentByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.findCustomersSaleWithPendindOrIncompletePaymentByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(final String unitUuid) throws BusinessException {
		return this.customerDAO.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(unitUuid, EntityStatus.ACTIVE);
	}
}
