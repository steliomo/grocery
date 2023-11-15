/**
 *
 */
package mz.co.grocery.persistence.pos.service;

import mz.co.grocery.persistence.pos.model.WhatsAppMessage;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

public interface WhatsAppAPIService {

	WhatsAppMessage sendMessage(WhatsAppMessage message) throws BusinessException;

}
