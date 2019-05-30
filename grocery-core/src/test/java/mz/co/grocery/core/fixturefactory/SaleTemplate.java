/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.sale.model.Sale;
import mz.co.grocery.core.sale.model.SaleItem;

/**
 * @author Stélio Moiane
 *
 */
public class SaleTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(Sale.class).addTemplate(VALID, new Rule() {
			{
				this.add("saleDate", LocalDate.now());
				this.add("items", this.has(10).of(SaleItem.class, SaleItemTemplate.VALID));
			}
		});
	}
}
