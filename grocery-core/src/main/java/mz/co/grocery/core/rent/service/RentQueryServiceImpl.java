/**
 *
 */
package mz.co.grocery.core.rent.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
@Service(RentQueryServiceImpl.NAME)
public class RentQueryServiceImpl implements RentQueryService {

	public static final String NAME = "mz.co.grocery.core.rent.service.RentQueryServiceImpl";

	@Inject
	private RentDAO rentDAO;

	@Override
	public List<Rent> findPendingPaymentRentsByCustomer(final String customerUuid) throws BusinessException {
		return this.rentDAO.findPendinPaymentsByCustomer(customerUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Rent> fetchPendingDevolutionRentsByCustomer(final String customerUuid) throws BusinessException {
		return this.rentDAO.fetchPendingDevolutionsByCustomer(customerUuid, EntityStatus.ACTIVE);
	}

}
