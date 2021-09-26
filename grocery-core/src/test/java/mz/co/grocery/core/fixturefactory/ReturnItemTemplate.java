/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.rent.model.ReturnItem;

/**
 * @author Stélio Moiane
 *
 */
public class ReturnItemTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	public static final String INVALID = "INVALID";

	@Override
	public void load() {
		Fixture.of(ReturnItem.class).addTemplate(ReturnItemTemplate.VALID, new Rule() {
			{
				this.add("rentItem", this.one(RentItem.class, RentItemTemplate.PRODUCT));
				this.add("returnDate", LocalDate.now());
				this.add("quantity", this.random(BigDecimal.class, this.range(BigDecimal.ONE, BigDecimal.TEN)));
			}
		});

		Fixture.of(ReturnItem.class).addTemplate(ReturnItemTemplate.INVALID, new Rule() {
			{
				this.add("rentItem", this.one(RentItem.class, RentItemTemplate.SERVICE));
			}
		});
	}

}
