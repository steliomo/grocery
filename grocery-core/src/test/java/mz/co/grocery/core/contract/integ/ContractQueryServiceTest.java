/**
 *
 */
package mz.co.grocery.core.contract.integ;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.service.ContractQueryService;
import mz.co.grocery.core.contract.service.ContractService;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.fixturefactory.ContractTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ContractQueryServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ContractService contractService;

	@Inject
	private CustomerService customerService;

	@Inject
	private GroceryService unitService;

	@Inject
	private ContractQueryService contractQueryService;

	private Contract contract;

	@Before
	public void setup() throws BusinessException {

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

		this.contractService.celebrateContract(this.getUserContext(), this.contract);
	}

	@Test
	public void shouldFindPendingContractsForPaymentByCustomer() throws BusinessException {

		final List<Contract> contracts = this.contractQueryService.findPendingContractsForPaymentByCustomerUuid(this.contract.getCustomer().getUuid(),
				LocalDate.now());

		Assert.assertFalse(contracts.isEmpty());
		contracts.forEach(contract -> {
			Assert.assertEquals(LocalDate.now(), contract.getReferencePaymentDate());
		});
	}
}
