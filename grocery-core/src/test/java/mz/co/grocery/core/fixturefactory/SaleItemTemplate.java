/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.stock.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemTemplate implements TemplateLoader {

	public static final String VALID = "VALID";
	public static final String SALE_VALUE = "SALE_VALUE";

	@Override
	public void load() {
		Fixture.of(SaleItem.class).addTemplate(VALID, new Rule() {
			{
				this.add("stock", this.one(Stock.class, StockTemplate.VALID));
				this.add("quantity", this.random(BigDecimal.class, this.range(1.0, 50.0)));
			}
		});

		Fixture.of(SaleItem.class).addTemplate(SALE_VALUE).inherits(VALID, new Rule() {
			{
				this.add("stock", this.one(Stock.class, StockTemplate.VALID));
				this.add("quantity", BigDecimal.ZERO);
				this.add("saleItemValue", this.random(BigDecimal.class, this.range(1.0, 50.0)));
			}
		});
	}
}
