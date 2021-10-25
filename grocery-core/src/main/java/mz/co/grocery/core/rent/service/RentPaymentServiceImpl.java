/**
 *
 */
package mz.co.grocery.core.rent.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.rent.dao.RentDAO;
import mz.co.grocery.core.rent.dao.RentPaymentDAO;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;
import mz.co.msaude.boot.frameworks.service.AbstractService;

/**
 * @author Stélio Moiane
 *
 */

@Service(RentPaymentServiceImpl.NAME)
public class RentPaymentServiceImpl extends AbstractService implements RentPaymentService {

	public static final String NAME = "mz.co.grocery.core.rent.service.RentPaymentServiceImpl";

	@Inject
	private RentPaymentDAO rentPaymentDAO;

	@Inject
	private RentDAO rentDAO;

	@Inject
	private PaymentService paymentService;

	@Override
	public RentPayment makeRentPayment(final UserContext userContext, final RentPayment rentPayment) throws BusinessException {
		final Rent rent = this.rentDAO.findByUuid(rentPayment.getRent().getUuid());

		this.rentPaymentDAO.create(userContext, rentPayment);

		rent.setTotalPaid(rentPayment.getPaymentValue());
		rent.setPaymentStatus();
		this.rentDAO.update(userContext, rent);

		this.paymentService.debitTransaction(userContext, rent.getUnit().getUuid());
		return rentPayment;
	}
}
