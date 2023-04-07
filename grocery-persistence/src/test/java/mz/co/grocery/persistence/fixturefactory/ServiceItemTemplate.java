/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.ServiceDescription;
import mz.co.grocery.core.saleable.model.ServiceItem;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(ServiceItem.class).addTemplate(ServiceItemTemplate.VALID, new Rule() {
			{
				this.add("serviceDescription", this.one(ServiceDescription.class, ServiceDescriptionTemplate.VALID));
				this.add("unit", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("salePrice", new BigDecimal("200.00"));
			}
		});
	}
}
