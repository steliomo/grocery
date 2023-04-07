/**
 *
 */
package mz.co.grocery.persistence.contract;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.service.ContractService;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.ContractTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ContractServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ContractService contractService;

	@Inject
	private CustomerService customerService;

	@Inject
	private GroceryService unitService;

	private Contract contract;

	@Before
	public void setup() {

		this.contract = EntityFactory.gimme(Contract.class, ContractTemplate.VALID, processor -> {
			if (processor instanceof Contract) {
				final Contract c = (Contract) processor;
				try {
					this.unitService.createGrocery(this.getUserContext(), c.getUnit());
					c.getCustomer().setUnit(c.getUnit());
					this.customerService.createCustomer(this.getUserContext(), c.getCustomer());
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Test
	public void shouldCelebrateContract() throws BusinessException {

		this.contractService.celebrateContract(this.getUserContext(), this.contract);

		TestUtil.assertCreation(this.contract);
	}
}
