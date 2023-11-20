/**
 *
 */
package mz.co.grocery.persistence.pos.adapter;

import mz.co.grocery.core.application.pos.out.SaleListner;
import mz.co.grocery.core.application.pos.out.SendMessagesPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.pos.Message;
import mz.co.grocery.core.domain.pos.MessageType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter(SendSaleTemplateMessageToWhastAppListner.NAME)
public class SendSaleTemplateMessageToWhastAppListner implements SaleListner {

	public static final String NAME = "mz.co.grocery.persistence.pos.adapter.SendSaleTemplateMessageToWhastAppListner";

	private SendMessagesPort sendMessagesPort;

	public SendSaleTemplateMessageToWhastAppListner(final SendMessagesPort sendMessagesPort) {
		this.sendMessagesPort = sendMessagesPort;
	}

	@Override
	public Sale send(final UserContext context, final Sale sale) throws BusinessException {

		final Message message = new Message(sale.getUnit().get().getName(), sale.getCustomer().get().getName(), sale.getCustomer().get().getContact(),
				MessageType.TEMPLATE);

		this.sendMessagesPort.sendMessages(message);

		return sale;
	}

}
