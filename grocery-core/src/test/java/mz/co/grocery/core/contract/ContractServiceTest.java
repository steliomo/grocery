/**
 *
 */
package mz.co.grocery.core.contract;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.contract.in.CelebrateContractUseCase;
import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.application.contract.service.ContractService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.fixturefactory.ContractTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ContractServiceTest extends AbstractUnitServiceTest {

	@Mock
	private ContractPort contractPort;

	@InjectMocks
	private final CelebrateContractUseCase contractService = new ContractService(this.contractPort);

	@Test
	public void shouldCelebrateContract() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID);
		contract.setEndDate(contract.getEndDate().plusDays(1));

		this.contractService.celebrateContract(this.getUserContext(), contract);

		Mockito.verify(this.contractPort, Mockito.times(1)).createContract(this.getUserContext(), contract);
		Assert.assertEquals(contract.getStartDate(), contract.getReferencePaymentDate());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotCelebrateContractForDurationLessThan30Days() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID);
		contract.setStartDate(LocalDate.now());
		contract.setEndDate(LocalDate.now());

		this.contractService.celebrateContract(this.getUserContext(), contract);
	}
}
