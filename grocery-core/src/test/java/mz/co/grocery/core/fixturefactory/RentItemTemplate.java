/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class RentItemTemplate implements TemplateLoader {

	public static final String VALID = "VALID";
	public static final String PRODUCT = "PRODUCTS";
	public static final String SERVICE = "SERVICE";

	@Override
	public void load() {
		Fixture.of(RentItem.class).addTemplate(RentItemTemplate.VALID, new Rule() {
			{
				this.add("plannedQuantity", this.random(BigDecimal.class, this.range(10, 50)));
				this.add("plannedDays", this.random(BigDecimal.class, this.range(1, 30)));
				this.add("discount", this.random(BigDecimal.class, this.range(0, 10)));
			}
		});

		Fixture.of(RentItem.class).addTemplate(RentItemTemplate.PRODUCT).inherits(RentItemTemplate.VALID, new Rule() {
			{
				this.add("stock", this.one(Stock.class, StockTemplate.VALID));
			}
		});

		Fixture.of(RentItem.class).addTemplate(RentItemTemplate.SERVICE).inherits(RentItemTemplate.VALID, new Rule() {
			{
				this.add("serviceItem", this.one(ServiceItem.class, ServiceTemplate.VALID));
			}
		});
	}

}
