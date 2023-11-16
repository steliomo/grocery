/**
 *
 */
package mz.co.grocery.core.application.pos.service;

import java.util.Set;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.pos.in.SendTableBillUseCase;
import mz.co.grocery.core.application.pos.out.SendMessagesPort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.pos.Bill;
import mz.co.grocery.core.domain.pos.Message;
import mz.co.grocery.core.domain.pos.MessageType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class SendTableBillService implements SendTableBillUseCase {

	private DocumentGeneratorPort documentGeneratorPort;

	private SendMessagesPort senMessagesPort;

	private Clock clock;

	public SendTableBillService(final DocumentGeneratorPort documentGeneratorPort, final SendMessagesPort sendMessagesPort, final Clock clock) {
		this.documentGeneratorPort = documentGeneratorPort;
		this.senMessagesPort = sendMessagesPort;
		this.clock = clock;
	}

	@Override
	public Sale sendBill(final Sale sale) throws BusinessException {
		final Set<SaleItem> items = sale.getItems().get();

		if (items.isEmpty()) {
			throw new BusinessException("pos.table.must.have.items");
		}

		sale.getCustomer();
		if (!sale.getCustomer().isPresent()) {
			throw new BusinessException("pos.table.must.have.customer");
		}

		final Document document = new Bill(sale, this.clock);
		final String filename = this.documentGeneratorPort.generatePdfDocument(document);

		sale.setFilename(filename);

		final Message textMessage = new Message(sale.getCustomer().get().getName(), sale.getCustomer().get().getContact(), MessageType.TEXT,
				Boolean.FALSE);

		final Message documentMessage = new Message(sale.getCustomer().get().getContact(), MessageType.DOCUMENT,
				sale.getFilename());

		this.senMessagesPort.sendMessages(textMessage, documentMessage);

		return sale;
	}

}
