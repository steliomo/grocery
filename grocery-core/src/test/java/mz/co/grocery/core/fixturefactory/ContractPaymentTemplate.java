/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.contract.model.Contract;
import mz.co.grocery.core.contract.model.ContractPayment;

/**
 * @author Stélio Moiane
 *
 */
public class ContractPaymentTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(ContractPayment.class).addTemplate(ContractPaymentTemplate.VALID, new Rule() {
			{
				this.add("contract", this.one(Contract.class, ContractTemplate.VALID));
				this.add("paymentDate", LocalDate.now());
			}
		});
	}
}
