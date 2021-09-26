/**
 *
 */
package mz.co.grocery.core.customer.service;

import java.util.List;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface CustomerQueryService {

	List<Customer> findCustomersByUnit(String unitUuid, int currentPage, int maxResult) throws BusinessException;

	Long countCustomersByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithPendingPeymentsByUnit(String unitUuid, int currentPage, int maxResult) throws BusinessException;

	Long countCustomersWithPendingPeymentsByUnit(String unitUuid) throws BusinessException;

	List<Customer> findCustomersWithPendingDevolutionsByUnit(String unitUuid, int currentPage, int maxResult) throws BusinessException;

	Long countCustomersWithPendingDevolutionsByUnit(String unitUuid) throws BusinessException;
}
