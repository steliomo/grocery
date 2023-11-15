/**
 *
 */
package mz.co.grocery.core.pos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.pos.in.SendTableBillUseCase;
import mz.co.grocery.core.application.pos.out.SendMessagesPort;
import mz.co.grocery.core.application.pos.service.SendTableBillService;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

public class SendTableBillUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private DocumentGeneratorPort documentGeneratorPort;

	@Mock
	private SendMessagesPort senMessagePort;

	@Mock
	private Clock clock;

	@InjectMocks
	private SendTableBillUseCase sendTableInvoiceUseCase = new SendTableBillService(this.documentGeneratorPort,
			this.senMessagePort, this.clock);

	@Test
	public void shouldSendInvoiceToWhatsApp() throws BusinessException {

		final Sale table = new Sale();

		table.setCustomer(new Customer());

		table.addItem(new SaleItem());

		Mockito.when(this.documentGeneratorPort.generatePdfDocument(ArgumentMatchers.any())).thenReturn("teste.pdf");

		this.sendTableInvoiceUseCase.sendBill(table);

		Mockito.verify(this.senMessagePort, Mockito.times(1)).sendMessages(ArgumentMatchers.any());
		Mockito.verify(this.documentGeneratorPort, Mockito.times(1)).generatePdfDocument(ArgumentMatchers.any());
		Assert.assertNotNull(table.getFilename());
	}
}
