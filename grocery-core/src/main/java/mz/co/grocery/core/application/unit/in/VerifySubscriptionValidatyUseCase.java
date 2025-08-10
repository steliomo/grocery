/**
 *
 */
package mz.co.grocery.core.application.unit.in;

import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface VerifySubscriptionValidatyUseCase {

	Boolean isSubscriptionValid(String unitUuid) throws BusinessException;

}
