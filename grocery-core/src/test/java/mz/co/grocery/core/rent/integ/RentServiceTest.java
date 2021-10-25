/**
 *
 */
package mz.co.grocery.core.rent.integ;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.rent.builder.RentBuilder;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.rent.service.RentService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class RentServiceTest extends AbstractIntegServiceTest {

	@Inject
	private RentBuilder rentBuilder;

	@Inject
	private RentService rentService;

	private Rent rent;

	@Before
	public void setup() throws BusinessException {

		this.rent = this.rentBuilder.build();
	}

	@Test
	public void shoulRentItens() throws BusinessException {

		this.rentService.rent(this.getUserContext(), this.rent);

		TestUtil.assertCreation(this.rent);
	}
}
