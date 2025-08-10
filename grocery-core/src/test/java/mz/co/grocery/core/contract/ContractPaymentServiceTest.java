/**
 *
 */
package mz.co.grocery.core.contract;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.contract.out.ContractPaymentPort;
import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.application.contract.service.ContractPaymentService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.grocery.core.fixturefactory.ContractPaymentTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPaymentServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ContractPaymentPort contractPaymentPort;

	@Mock
	private ContractPort contractPort;

	@InjectMocks
	private ContractPaymentService contractPaymentService;

	@Test
	public void shouldPerformContractPayment() throws BusinessException {

		final ContractPayment contractPayment = EntityFactory.gimme(ContractPayment.class, ContractPaymentTemplate.VALID, processor -> {
			if (processor instanceof ContractPayment) {
				((ContractPayment) processor).getContract().get().setReferencePaymentDate();
			}
		});

		Mockito.when(this.contractPort.findcontractById(contractPayment.getContract().get().getId())).thenReturn(contractPayment.getContract().get());

		this.contractPaymentService.performContractPayment(this.getUserContext(), contractPayment);

		Mockito.verify(this.contractPaymentPort, Mockito.times(1)).createContractPayment(this.getUserContext(), contractPayment);
		Mockito.verify(this.contractPort, Mockito.times(1)).updateContract(this.getUserContext(), contractPayment.getContract().get());

		Assert.assertEquals(contractPayment.getContract().get().getTotalPaid(), new BigDecimal(5000));
	}
}
