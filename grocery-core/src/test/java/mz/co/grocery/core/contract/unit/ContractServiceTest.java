/**
 *
 */
package mz.co.grocery.core.contract.unit;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.contract.dao.ContractDAO;
import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.service.ContractService;
import mz.co.grocery.core.contract.service.ContractServiceImpl;
import mz.co.grocery.core.fixturefactory.ContractTemplate;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ContractServiceTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final ContractService contractService = new ContractServiceImpl();

	@Mock
	private ContractDAO contractDAO;

	@Mock
	private ApplicationTranslator translator;

	@Test
	public void shouldCelebrateContract() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID);
		contract.setEndDate(contract.getEndDate().plusDays(1));

		this.contractService.celebrateContract(this.getUserContext(), contract);

		Mockito.verify(this.contractDAO, Mockito.times(1)).create(this.getUserContext(), contract);
		Assert.assertEquals(contract.getStartDate(), contract.getReferencePaymentDate());
		Assert.assertEquals(contract.getStartDate().getDayOfMonth(), contract.getEndDate().getDayOfMonth());
	}

	@Test(expected = BusinessException.class)
	public void shouldNotCelebrateContractForDurationLessThan30Days() throws BusinessException {

		final Contract contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID);
		contract.setStartDate(LocalDate.now());
		contract.setEndDate(LocalDate.now());

		this.contractService.celebrateContract(this.getUserContext(), contract);
	}
}
