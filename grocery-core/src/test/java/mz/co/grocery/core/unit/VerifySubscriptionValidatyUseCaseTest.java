/**
 *
 */
package mz.co.grocery.core.unit;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.application.unit.service.VerifySubscriptionValidatyService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class VerifySubscriptionValidatyUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private UnitPort unitPort;

	@InjectMocks
	private VerifySubscriptionValidatyService verifySubscriptionValidatyUseCase;

	@Test
	public void shouldVerifySubscriptionValidatyToday() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		final Boolean isValid = this.verifySubscriptionValidatyUseCase.isSubscriptionValid(unit.getUuid());

		Assert.assertTrue(isValid);
	}

	@Test(expected = BusinessException.class)
	public void shouldVerifySubscriptionValidatyWhenItIsNull() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit.setSubscriptionEndDate(null);
		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		this.verifySubscriptionValidatyUseCase.isSubscriptionValid(unit.getUuid());
	}

	@Test
	public void shouldVerifySubscriptionValidatyWhenItIsAfterToday() throws BusinessException {

		final Unit unit = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		unit.setSubscriptionEndDate(LocalDate.now().plusDays(1));
		Mockito.when(this.unitPort.findByUuid(unit.getUuid())).thenReturn(unit);

		final Boolean isValid = this.verifySubscriptionValidatyUseCase.isSubscriptionValid(unit.getUuid());

		Assert.assertTrue(isValid);
	}
}
