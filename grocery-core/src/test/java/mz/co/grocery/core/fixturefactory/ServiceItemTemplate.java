/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;

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
				this.add("unit", this.one(Unit.class, UnitTemplate.VALID));
				this.add("salePrice", new BigDecimal("200.00"));
			}
		});
	}
}
