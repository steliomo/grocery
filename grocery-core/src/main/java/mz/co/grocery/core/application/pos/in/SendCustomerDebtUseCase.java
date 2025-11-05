/**
 *
 */
package mz.co.grocery.core.application.pos.in;

import mz.co.grocery.core.domain.pos.Debt;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface SendCustomerDebtUseCase {

	void sendDebt(Debt debt) throws BusinessException;

}
