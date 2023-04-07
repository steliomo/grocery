/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.core.grocery.model.Grocery;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationTemplate implements TemplateLoader {

	public static final String RENT = "RENT";
	public static final String RENT_WITH_ITEMS = "RENT_WITH_ITEMS";

	@Override
	public void load() {
		Fixture.of(Quotation.class).addTemplate(QuotationTemplate.RENT, new Rule() {
			{
				this.add("type", QuotationType.RENT);
			}
		});

		Fixture.of(Quotation.class).addTemplate(QuotationTemplate.RENT_WITH_ITEMS).inherits(QuotationTemplate.RENT, new Rule() {
			{
				this.add("type", QuotationType.RENT);
				this.add("unit", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("items", this.has(5).of(QuotationItem.class, QuotationItemTemplate.PRODUCT));
			}
		});
	}

}
