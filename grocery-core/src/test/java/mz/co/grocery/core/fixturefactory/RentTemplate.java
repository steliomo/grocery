/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.rent.model.PaymentStatus;
import mz.co.grocery.core.rent.model.Rent;

/**
 * @author Stélio Moiane
 *
 */
public class RentTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(Rent.class).addTemplate(RentTemplate.VALID, new Rule() {
			{
				this.add("unit", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("rentDate", LocalDate.now());
				this.add("paymentStatus", PaymentStatus.PENDING);
				this.add("customer", this.one(Customer.class, CustomerTemplate.VALID));
			}
		});
	}
}
