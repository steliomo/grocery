/**
 *
 */
package mz.co.grocery.core.application.rent.out;

import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface RentPort {

	List<Rent> findPendingPaymentRentsByCustomer(String customerUuid) throws BusinessException;

	List<Rent> fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(String customerUuid) throws BusinessException;

	List<Rent> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(String customerUuid) throws BusinessException;

	List<Rent> fetchRentsWithIssuedGuidesByTypeAndCustomer(GuideType guideType, String customerUuid) throws BusinessException;

	List<Rent> fetchRentsWithPaymentsByCustomer(String customerUuid) throws BusinessException;

	Rent fetchByUuid(String uuid) throws BusinessException;

	Rent findByUuid(String uuid) throws BusinessException;

	Rent updateRent(UserContext userContext, Rent rent) throws BusinessException;

	Rent createRent(UserContext userContext, Rent rent) throws BusinessException;

	Optional<Rent> findRentByCustomerAndUnitAndStatus(Customer customer, Unit unit, RentStatus rentStatus) throws BusinessException;
}
