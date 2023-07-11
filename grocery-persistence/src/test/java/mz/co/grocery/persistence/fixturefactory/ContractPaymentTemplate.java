/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractPayment;

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
