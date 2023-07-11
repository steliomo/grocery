/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.sale.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemTemplate implements TemplateLoader {

	public static final String PRODUCT = "PRODUCT";
	public static final String SERVICE = "SERVICE";

	@Override
	public void load() {
		Fixture.of(SaleItem.class).addTemplate(SaleItemTemplate.PRODUCT, new Rule() {
			{
				this.add("stock", this.one(Stock.class, StockTemplate.VALID));
				this.add("quantity", this.random(BigDecimal.class, this.range(10.0, 50.0)));
				this.add("discount", this.random(BigDecimal.class, this.range(1.0, 10.0)));
				this.add("saleItemValue", this.random(BigDecimal.class, this.range(100.0, 500.0)));
			}
		});

		Fixture.of(SaleItem.class).addTemplate(SaleItemTemplate.SERVICE, new Rule() {
			{
				this.add("serviceItem", this.one(ServiceItem.class, ServiceItemTemplate.VALID));
				this.add("quantity", this.random(BigDecimal.class, this.range(10.0, 50.0)));
				this.add("discount", this.random(BigDecimal.class, this.range(1.0, 10.0)));
				this.add("saleItemValue", this.random(BigDecimal.class, this.range(100.0, 500.0)));
			}
		});
	}
}
