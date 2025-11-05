/**
 *
 */
package mz.co.grocery.core.application.pos.service;

import java.util.List;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.pos.in.SendCustomerDebtUseCase;
import mz.co.grocery.core.application.pos.out.SendMessagesPort;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.document.Document;
import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.grocery.core.domain.pos.DebtReport;
import mz.co.grocery.core.domain.pos.Message;
import mz.co.grocery.core.domain.pos.MessageType;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class SendCustomerDeptService implements SendCustomerDebtUseCase {

	private Clock clock;

	private DocumentGeneratorPort documentGeneratorPort;

	private SendMessagesPort sendMessagesPort;

	private SaleItemPort saleItemPort;

	public SendCustomerDeptService(final Clock clock, final DocumentGeneratorPort documentGeneratorPort, final SendMessagesPort sendMessagesPort,
			final SaleItemPort saleItemPort) {
		this.clock = clock;
		this.documentGeneratorPort = documentGeneratorPort;
		this.sendMessagesPort = sendMessagesPort;
		this.saleItemPort = saleItemPort;

	}

	@Override
	public void sendDebt(final Debt debt) throws BusinessException {

		if (!debt.getCustomer().isPresent()) {
			throw new BusinessException("pos.send.debt.must.have.customer");
		}

		final Customer customer = debt.getCustomer().get();
		final List<DebtItem> debtItems = this.saleItemPort.findDeptItemsByCustomer(customer.getUuid());

		if (debtItems.isEmpty()) {
			throw new BusinessException("pos.send.debt.items.cannot.be.empty");
		}

		debtItems.forEach(debtItem -> debt.addDebtItem(debtItem));

		final Document document = new DebtReport(debt, this.clock);
		final String filename = this.documentGeneratorPort.generatePdfDocument(document);

		final Message textMessage = new Message(customer.getName(),
				customer.getContact(), MessageType.TEXT,
				Boolean.FALSE);

		final Message documentMessage = new Message(customer.getContact(), MessageType.DOCUMENT,
				filename);

		this.sendMessagesPort.sendMessages(textMessage, documentMessage);
	}
}
