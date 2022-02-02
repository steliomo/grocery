/**
 *
 */
package mz.co.grocery.core.contract.integ;

import javax.inject.Inject;

import org.junit.Test;

import mz.co.grocery.core.config.AbstractIntegServiceTest;
import mz.co.grocery.core.contract.model.ContractPayment;
import mz.co.grocery.core.contract.service.ContractPaymentService;
import mz.co.grocery.core.contract.service.ContractService;
import mz.co.grocery.core.customer.service.CustomerService;
import mz.co.grocery.core.fixturefactory.ContractPaymentTemplate;
import mz.co.grocery.core.grocery.service.GroceryService;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPaymentServiceTest extends AbstractIntegServiceTest {

	@Inject
	private ContractPaymentService contractPaymentService;

	@Inject
	private ContractService contractService;

	@Inject
	private GroceryService unitService;

	@Inject
	private CustomerService customerService;

	@Test
	public void shouldPerformContractPayment() throws BusinessException {
		final ContractPayment contractPayment = EntityFactory.gimme(ContractPayment.class, ContractPaymentTemplate.VALID, processor -> {
			if (processor instanceof ContractPayment) {
				final ContractPayment cp = (ContractPayment) processor;
				try {
					this.unitService.createGrocery(this.getUserContext(), cp.getContract().getUnit());
					cp.getContract().getCustomer().setUnit(cp.getContract().getUnit());
					this.customerService.createCustomer(this.getUserContext(), cp.getContract().getCustomer());
					this.contractService.celebrateContract(this.getUserContext(), cp.getContract());
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		this.contractPaymentService.performContractPayment(this.getUserContext(), contractPayment);

		TestUtil.assertCreation(contractPayment);
	}
}
