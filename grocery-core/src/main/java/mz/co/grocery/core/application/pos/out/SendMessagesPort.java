/**
 *
 */
package mz.co.grocery.core.application.pos.out;

import mz.co.grocery.core.domain.pos.Message;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

public interface SendMessagesPort {

	void sendMessages(Message... messages) throws BusinessException;
}
