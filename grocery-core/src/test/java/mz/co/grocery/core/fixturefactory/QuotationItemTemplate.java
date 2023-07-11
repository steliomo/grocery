/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.sale.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationItemTemplate implements TemplateLoader {

	public static final String PRODUCT = "PRODUCT";

	@Override
	public void load() {
		Fixture.of(QuotationItem.class).addTemplate(QuotationItemTemplate.PRODUCT, new Rule() {
			{
				this.add("item", this.one(Stock.class, StockTemplate.VALID));
				this.add("quantity", this.random(BigDecimal.class, this.range(new BigDecimal(1), new BigDecimal(10))));
				this.add("days", this.random(BigDecimal.class, this.range(new BigDecimal(1), new BigDecimal(20))));
			}
		});
	}
}
