/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;
import java.util.HashSet;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;

/**
 * @author Stélio Moiane
 *
 */
public class SaleTemplate implements TemplateLoader {

	public static final String VALID = "VALID";
	public static final String WITH_ITEMS = "WITH_ITEMS";

	@Override
	public void load() {
		Fixture.of(Sale.class).addTemplate(SaleTemplate.VALID, new Rule() {
			{
				this.add("grocery", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("saleDate", LocalDate.now());
				this.add("items", new HashSet<SaleItem>());
			}
		});

		Fixture.of(Sale.class).addTemplate(SaleTemplate.WITH_ITEMS).inherits(SaleTemplate.VALID, new Rule() {
			{
				this.add("items", this.has(10).of(SaleItem.class, SaleItemTemplate.PRODUCT));
			}
		});
	}
}
