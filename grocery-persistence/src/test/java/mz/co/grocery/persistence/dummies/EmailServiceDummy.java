/**
 *
 */
package mz.co.grocery.persistence.dummies;

import mz.co.grocery.core.application.email.out.EmailPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class EmailServiceDummy implements EmailPort {

	@Override
	public void send(final EmailDetails emailDetails) throws BusinessException {

	}
}
