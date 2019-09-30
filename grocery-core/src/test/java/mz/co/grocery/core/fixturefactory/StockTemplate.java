/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.product.model.ProductDescription;
import mz.co.grocery.core.stock.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class StockTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(Stock.class).addTemplate(VALID, new Rule() {
			{
				this.add("grocery", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("productDescription", this.one(ProductDescription.class, ProductDescriptionTemplate.VALID));
				this.add("purchasePrice", this.random(BigDecimal.class, this.range(1, 100)));
				this.add("salePrice", this.random(BigDecimal.class, this.range(100, 200)));
				this.add("quantity", new BigDecimal("200.5"));
			}
		});
	}
}
