/**
 *
 */
package mz.co.grocery.core.application.email.out;

import mz.co.grocery.core.domain.email.EmailDetails;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

public interface EmailPort {
	void send(EmailDetails emailDetails) throws BusinessException;
}
