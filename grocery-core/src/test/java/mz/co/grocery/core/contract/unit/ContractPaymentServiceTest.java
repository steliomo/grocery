/**
 *
 */
package mz.co.grocery.core.contract.unit;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.contract.dao.ContractDAO;
import mz.co.grocery.core.contract.dao.ContractPaymentDAO;
import mz.co.grocery.core.contract.model.ContractPayment;
import mz.co.grocery.core.contract.service.ContractPaymentService;
import mz.co.grocery.core.contract.service.ContractPaymentServiceImpl;
import mz.co.grocery.core.fixturefactory.ContractPaymentTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPaymentServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final ContractPaymentService contractPaymentService = new ContractPaymentServiceImpl();

	@Mock
	private ContractPaymentDAO contractPaymentDAO;

	@Mock
	private ContractDAO contractDAO;

	@Mock
	private ApplicationTranslator translator;

	@Test
	public void shouldPerformContractPayment() throws BusinessException {

		final ContractPayment contractPayment = EntityFactory.gimme(ContractPayment.class, ContractPaymentTemplate.VALID, processor -> {
			if (processor instanceof ContractPayment) {
				((ContractPayment) processor).getContract().setReferencePaymentDate();
			}
		});

		Mockito.when(this.contractDAO.findById(contractPayment.getContract().getId())).thenReturn(contractPayment.getContract());

		this.contractPaymentService.performContractPayment(this.getUserContext(), contractPayment);

		Mockito.verify(this.contractPaymentDAO, Mockito.times(1)).create(this.getUserContext(), contractPayment);
		Mockito.verify(this.contractDAO, Mockito.times(1)).update(this.getUserContext(), contractPayment.getContract());

		Assert.assertEquals(contractPayment.getContract().getTotalPaid(), new BigDecimal(5000));
	}
}
