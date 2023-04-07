/**
 *
 */
package mz.co.grocery.core.quotation;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.quotation.in.IssueQuotationUseCase;
import mz.co.grocery.core.application.quotation.out.GenerateQuotationPdfPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationItemPort;
import mz.co.grocery.core.application.quotation.out.SaveQuotationPort;
import mz.co.grocery.core.application.quotation.service.IssueQuotationService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.quotation.QuotationStatus;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.core.fixturefactory.QuotationTemplate;
import mz.co.grocery.core.payment.service.PaymentService;
import mz.co.grocery.core.util.Clock;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class IssueQuotationUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private Clock clock;

	@Mock
	private SaveQuotationPort saveQuotationPort;

	@Mock
	private SaveQuotationItemPort quotationItemPort;

	@Mock
	private GenerateQuotationPdfPort generateQuotationPdfPort;

	@Mock
	private PaymentService paymentService;

	@InjectMocks
	private final IssueQuotationUseCase issueQuotationUseCase = new IssueQuotationService(this.clock, this.saveQuotationPort, this.quotationItemPort,
			this.generateQuotationPdfPort, this.paymentService);

	@Test
	public void shouldIssueRentQuotation() throws BusinessException {
		final Quotation quotation = EntityFactory.gimme(Quotation.class, QuotationTemplate.RENT_WITH_ITEMS);
		Mockito.when(this.clock.todayDate()).thenReturn(LocalDate.now());

		this.issueQuotationUseCase.issueQuotation(this.getUserContext(), quotation);

		Mockito.verify(this.saveQuotationPort, Mockito.times(1)).save(this.getUserContext(), quotation);
		Mockito.verify(this.quotationItemPort, Mockito.times(5)).save(ArgumentMatchers.any(UserContext.class),
				ArgumentMatchers.any(QuotationItem.class));
		Mockito.verify(this.generateQuotationPdfPort, Mockito.times(1)).generatePdf(quotation);
		Mockito.verify(this.paymentService, Mockito.times(1)).debitTransaction(this.getUserContext(), quotation.getUnit().getUuid());

		Assert.assertEquals(QuotationType.RENT, quotation.getType());
		Assert.assertEquals(QuotationStatus.PENDING, quotation.getStatus());
		Assert.assertNotNull(quotation.getIssueDate());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotIssueQuotationWithoutItems() throws BusinessException {
		final Quotation quotation = EntityFactory.gimme(Quotation.class, QuotationTemplate.RENT);
		this.issueQuotationUseCase.issueQuotation(this.getUserContext(), quotation);
	}
}
