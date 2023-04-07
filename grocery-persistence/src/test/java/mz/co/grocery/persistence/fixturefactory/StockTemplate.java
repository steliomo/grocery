/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.ProductDescription;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.saleable.model.StockStatus;
import mz.co.msaude.boot.frameworks.util.UuidFactory;

/**
 * @author Stélio Moiane
 *
 */
public class StockTemplate implements TemplateLoader {

	public static final String VALID = "VALID";
	public static final String WITH_UUID = "WITH_UUID";
	public static final String UNAVAILABLE = "UNAVAILABLE";
	public static final String IN_ANALYSIS = "IN_ANALYSIS";

	@Override
	public void load() {

		Fixture.of(Stock.class).addTemplate(StockTemplate.VALID, new Rule() {
			{
				this.add("grocery", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("productDescription", this.one(ProductDescription.class, ProductDescriptionTemplate.VALID));
				this.add("purchasePrice", this.random(BigDecimal.class, this.range(1, 100)));
				this.add("salePrice", this.random(BigDecimal.class, this.range(100, 200)));
				this.add("quantity", new BigDecimal("200.5"));
				this.add("minimumStock", new BigDecimal("10"));
				this.add("rentPrice", this.random(BigDecimal.class, this.range(100, 200)));
			}
		});

		Fixture.of(Stock.class).addTemplate(StockTemplate.WITH_UUID).inherits(StockTemplate.VALID, new Rule() {
			{
				this.add("uuid", UuidFactory.generate());
			}
		});

		Fixture.of(Stock.class).addTemplate(StockTemplate.UNAVAILABLE).inherits(StockTemplate.VALID, new Rule() {
			{
				this.add("quantity", BigDecimal.ZERO);
			}
		});

		Fixture.of(Stock.class).addTemplate(StockTemplate.IN_ANALYSIS).inherits(StockTemplate.VALID, new Rule() {
			{
				this.add("quantity", this.random(BigDecimal.class, this.range(new BigDecimal(0), new BigDecimal(10))));
				this.add("inventoryDate", LocalDate.now());
				this.add("inventoryQuantity", this.random(BigDecimal.class, this.range(new BigDecimal(11), new BigDecimal(20))));
				this.add("productStockStatus", StockStatus.BAD);
			}
		});
	}
}
