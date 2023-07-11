/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;
import java.util.HashSet;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.customer.SaleType;
import mz.co.grocery.core.domain.sale.Sale;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.SaleStatus;
import mz.co.grocery.core.domain.unit.Unit;

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
				this.add("unit", this.one(Unit.class, UnitTemplate.VALID));
				this.add("saleDate", LocalDate.now());
				this.add("items", new HashSet<SaleItem>());
				this.add("saleType", this.random(SaleType.CASH, SaleType.INSTALLMENT));
				this.add("saleStatus", this.random(SaleStatus.PENDING, SaleStatus.INCOMPLETE, SaleStatus.COMPLETE));
			}
		});

		Fixture.of(Sale.class).addTemplate(SaleTemplate.WITH_ITEMS).inherits(SaleTemplate.VALID, new Rule() {
			{
				this.add("items", this.has(10).of(SaleItem.class, SaleItemTemplate.PRODUCT));
			}
		});
	}
}
