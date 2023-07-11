/**
 *
 */
package mz.co.grocery.persistence.contract;

import javax.inject.Inject;

import org.junit.Test;

import mz.co.grocery.core.application.contract.in.CelebrateContractUseCase;
import mz.co.grocery.core.application.contract.in.PaymentContractUseCase;
import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractPayment;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.contract.entity.ContractPaymentEntityMapper;
import mz.co.grocery.persistence.fixturefactory.ContractPaymentTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.util.TestUtil;

/**
 * @author Stélio Moiane
 *
 */
public class PaymentContractUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private PaymentContractUseCase paymentContractUseCase;

	@Inject
	private CelebrateContractUseCase celebrateContractUseCase;

	@Inject
	private UnitPort unitPort;

	@Inject
	private CustomerPort customerPort;

	@Inject
	private ContractPaymentEntityMapper contractPaymentEntityMapper;

	@Test
	public void shouldPerformContractPayment() throws BusinessException {
		ContractPayment contractPayment = EntityFactory.gimme(ContractPayment.class, ContractPaymentTemplate.VALID, processor -> {
			if (processor instanceof ContractPayment) {
				final ContractPayment cp = (ContractPayment) processor;
				try {
					final Unit unit = this.unitPort.createUnit(this.getUserContext(), cp.getContract().get().getUnit().get());
					Customer customer = cp.getContract().get().getCustomer().get();
					customer.setUnit(unit);

					customer = this.customerPort.createCustomer(this.getUserContext(), customer);
					Contract contract = cp.getContract().get();

					contract.setUnit(unit);
					contract.setCustomer(customer);

					contract = this.celebrateContractUseCase.celebrateContract(this.getUserContext(), contract);
					cp.setContract(contract);
				} catch (final BusinessException e) {
					e.printStackTrace();
				}
			}
		});

		contractPayment = this.paymentContractUseCase.performContractPayment(this.getUserContext(), contractPayment);

		TestUtil.assertCreation(this.contractPaymentEntityMapper.toEntity(contractPayment));
	}
}
