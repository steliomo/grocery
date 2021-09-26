/**
 *
 */
package mz.co.grocery.core.rent.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.fixturefactory.RentPaymentTemplate;
import mz.co.grocery.core.rent.builder.RentBuilder;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.model.RentPayment;
import mz.co.grocery.core.rent.service.RentPaymentService;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentServiceTest extends AbstractIntegServiceTest {

	@Inject
	private RentService rentService;

	@Inject
	private RentPaymentService rentPaymentService;

	@Inject
	private RentBuilder rentBuilder;

	private Rent rent;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.rentBuilder.build();

		this.rentService.rent(this.getUserContext(), this.rent);
	}

	@Test
	public void shouldMakeRentPayment() throws BusinessException {

		final RentPayment rentPayment = EntityFactory.gimme(RentPayment.class, RentPaymentTemplate.VALID, processor -> {
			if (processor instanceof RentPayment) {
				final RentPayment payment = (RentPayment) processor;
				payment.setRent(this.rent);
			}
		});

		this.rentPaymentService.makeRentPayment(this.getUserContext(), rentPayment);

		TestUtil.assertCreation(rentPayment);
	}
}
