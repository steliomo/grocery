/**
 *
 */
package mz.co.grocery.core.application.unit.service;

import mz.co.grocery.core.application.unit.in.VerifySubscriptionValidatyUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class VerifySubscriptionValidatyService implements VerifySubscriptionValidatyUseCase {

	private UnitPort unitPort;

	public VerifySubscriptionValidatyService(final UnitPort unitPort) {
		this.unitPort = unitPort;
	}

	@Override
	public Boolean isSubscriptionValid(final String unitUuid) throws BusinessException {

		final Unit unit = this.unitPort.findByUuid(unitUuid);

		if (!unit.isSubscriptionValid()) {
			throw new BusinessException("your.subscrition.is.invalid");
		}

		return Boolean.TRUE;
	}
}
