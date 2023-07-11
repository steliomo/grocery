/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentPayment;

/**
 * @author Stélio Moiane
 *
 */
public class RentPaymentTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(RentPayment.class).addTemplate(RentPaymentTemplate.VALID, new Rule() {
			{
				this.add("rent", this.one(Rent.class, RentTemplate.VALID));
				this.add("paymentDate", LocalDate.now());
				this.add("paymentValue", this.random(BigDecimal.class, this.range(10, 100)));
			}
		});

	}
}
