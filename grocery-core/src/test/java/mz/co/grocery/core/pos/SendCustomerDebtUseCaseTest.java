/**
 *
 */
package mz.co.grocery.core.pos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.application.pos.out.SendMessagesPort;
import mz.co.grocery.core.application.pos.service.SendCustomerDeptService;
import mz.co.grocery.core.application.sale.out.SaleItemPort;
import mz.co.grocery.core.common.Clock;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.pos.Debt;
import mz.co.grocery.core.domain.pos.DebtItem;
import mz.co.grocery.core.fixturefactory.CustomerTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class SendCustomerDebtUseCaseTest extends AbstractUnitServiceTest {

	@InjectMocks
	private SendCustomerDeptService sendCustomerDeptUseCase;

	@Mock
	private DocumentGeneratorPort documentGeneratorPort;

	@Mock
	private SendMessagesPort sendMessagesPort;

	@Mock
	private Clock clock;

	@Mock
	private SaleItemPort saleItemPort;

	@Test
	public void shouldSendCustomerDept() throws BusinessException {

		final Debt debt = new Debt(new BigDecimal(1000), new BigDecimal(50));
		debt.setCustomer(EntityFactory.gimme(Customer.class, CustomerTemplate.VALID));
		debt.addDebtItem(new DebtItem(LocalDate.now(), "Sumo Davita 1L", new BigDecimal(2), new BigDecimal(100), new BigDecimal(200)));

		final List<DebtItem> debtItems = new ArrayList<>();
		debtItems.add(new DebtItem(LocalDate.now(), "TESTE", new BigDecimal(10), new BigDecimal(5), new BigDecimal(50)));

		Mockito.when(this.clock.todayDateTime()).thenReturn(LocalDateTime.now());
		Mockito.when(this.saleItemPort.findDeptItemsByCustomer(debt.getCustomer().get().getUuid())).thenReturn(debtItems);

		this.sendCustomerDeptUseCase.sendDebt(debt);

		Mockito.verify(this.documentGeneratorPort, Mockito.times(1)).generatePdfDocument(ArgumentMatchers.any());
		Mockito.verify(this.sendMessagesPort, Mockito.times(1)).sendMessages(ArgumentMatchers.any());
	}

}
