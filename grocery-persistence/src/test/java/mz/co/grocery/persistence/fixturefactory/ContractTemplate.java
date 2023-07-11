/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.contract.ContractType;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class ContractTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(Contract.class).addTemplate(ContractTemplate.VALID, new Rule() {
			{
				this.add("contractType", this.random(ContractType.PROPERTY_RENTAL, ContractType.VEHICLE_RENTAL, ContractType.SERVICE_PROVISION));
				this.add("description", "Contracto de arrentamento");
				this.add("startDate", LocalDate.now());
				this.add("endDate", LocalDate.now().plusMonths(3));
				this.add("monthlyPayment", new BigDecimal("5000"));
				this.add("unit", this.one(Unit.class, UnitTemplate.VALID));
				this.add("customer", this.one(Customer.class, CustomerTemplate.VALID));
			}
		});
	}

}
