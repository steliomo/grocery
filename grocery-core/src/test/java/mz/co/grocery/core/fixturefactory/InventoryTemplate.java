/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.inventory.model.Inventory;
import mz.co.grocery.core.inventory.model.InventoryStatus;
import mz.co.grocery.core.inventory.model.StockInventory;

/**
 * @author Stélio Moiane
 *
 */
public class InventoryTemplate implements TemplateLoader {

	public static final String VALID = "VALID";
	public static final String WITH_STOCKS = "WITH_STOCKS";
	public static final String WITH_STOCKS_WITH_UUID = "WITH_STOCKS_WITH_UUID";

	@Override
	public void load() {

		Fixture.of(Inventory.class).addTemplate(VALID, new Rule() {
			{
				this.add("inventoryDate", LocalDate.now());
				this.add("grocery", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("inventoryStatus", InventoryStatus.PENDING);
			}
		});

		Fixture.of(Inventory.class).addTemplate(WITH_STOCKS).inherits(VALID, new Rule() {
			{
				this.add("stockInventories", this.has(10).of(StockInventory.class, StockInventoryTemplate.VALID));
			}
		});

		Fixture.of(Inventory.class).addTemplate(WITH_STOCKS_WITH_UUID).inherits(VALID, new Rule() {
			{
				this.add("stockInventories", this.has(10).of(StockInventory.class, StockInventoryTemplate.WITH_UUID));
			}
		});

	}
}
