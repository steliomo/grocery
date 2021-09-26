/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.util.List;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface RentQueryService {

	List<Rent> findPendingPaymentRentsByCustomer(String customerUuid) throws BusinessException;

	List<Rent> fetchPendingDevolutionRentsByCustomer(String customerUuid) throws BusinessException;

}
