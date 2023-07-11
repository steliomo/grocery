/**
 *
 */
package mz.co.grocery.persistence.contract;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.contract.in.CelebrateContractUseCase;
import mz.co.grocery.core.application.contract.out.ContractPort;
import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ContractTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPortTest extends AbstractIntegServiceTest {

	@Inject
	private CelebrateContractUseCase cleCelebrateContractUseCase;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private UnitPort unitPort;

	@Inject
	private ContractPort contractPort;

	private Contract contract;

	@Before
	public void setup() throws BusinessException {

		this.contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID, processor -> {
			if (processor instanceof Contract) {
				final Contract c = (Contract) processor;
				try {
					final Unit unit = this.unitPort.createUnit(this.getUserContext(), c.getUnit().get());
					Customer customer = c.getCustomer().get();
					customer.setUnit(unit);

					customer = this.customerPort.createCustomer(this.getUserContext(), customer);

					c.setUnit(unit);
					c.setCustomer(customer);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		this.cleCelebrateContractUseCase.celebrateContract(this.getUserContext(), this.contract);
	}

	@Test
	public void shouldFindPendingContractsForPaymentByCustomer() throws BusinessException {

		final List<Contract> contracts = this.contractPort.findPendingContractsForPaymentByCustomerUuid(this.contract.getCustomer().get().getUuid(),
				LocalDate.now());

		Assert.assertFalse(contracts.isEmpty());
		contracts.forEach(contract -> {
			Assert.assertEquals(LocalDate.now(), contract.getReferencePaymentDate());
		});
	}
}
