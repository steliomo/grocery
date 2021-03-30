/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.StockInventory;
import mz.co.grocery.core.saleable.model.Stock;

/**
 * @author Stélio Moiane
 *
 */
public class StockInventoryTemplate implements TemplateLoader {

	public static final String VALID = "VALID";
	public static final String WITH_UUID = "WITH_UUID";

	@Override
	public void load() {

		Fixture.of(StockInventory.class).addTemplate(VALID, new Rule() {
			{
				this.add("inventory", this.one(Inventory.class, InventoryTemplate.VALID));
				this.add("stock", this.one(Stock.class, StockTemplate.VALID));
				this.add("fisicalInventory", this.random(BigDecimal.class, this.range(100, 200)));
			}
		});

		Fixture.of(StockInventory.class).addTemplate(WITH_UUID).inherits(VALID, new Rule() {
			{
				this.add("stock", this.one(Stock.class, StockTemplate.WITH_UUID));
			}
		});
	}

}
