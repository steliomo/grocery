/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.customer.model.Customer;

/**
 * @author Stélio Moiane
 *
 */
public class CustomerTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(Customer.class).addTemplate(CustomerTemplate.VALID, new Rule() {
			{
				this.add("name", this.random("Stelio Moiane", "Alima Moiane"));
				this.add("address", "Bairro Djuba, Q2, casa nr.375, Matola-rio Boane");
				this.add("contact", "+258840546824");
				this.add("email", "steliomo@gmail.com");
				this.add("vehicleNumberPlate", "AGE-769-MC");
			}
		});

	}

}
