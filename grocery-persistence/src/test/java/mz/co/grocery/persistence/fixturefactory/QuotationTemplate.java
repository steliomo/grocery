/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.quotation.Quotation;
import mz.co.grocery.core.domain.quotation.QuotationItem;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.core.domain.unit.Unit;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationTemplate implements TemplateLoader {

	public static final String RENT = "RENT";
	public static final String RENT_WITH_ITEMS = "RENT_WITH_ITEMS";
	public static final String SALE = "SALE";
	public static final String SALE_WITH_ITEMS = "SALE_WITH_ITEMS";

	@Override
	public void load() {
		Fixture.of(Quotation.class).addTemplate(QuotationTemplate.RENT, new Rule() {
			{
				this.add("type", QuotationType.RENT);
			}
		});

		Fixture.of(Quotation.class).addTemplate(QuotationTemplate.SALE, new Rule() {
			{
				this.add("type", QuotationType.SALE);
			}
		});

		Fixture.of(Quotation.class).addTemplate(QuotationTemplate.RENT_WITH_ITEMS).inherits(QuotationTemplate.RENT, new Rule() {
			{
				this.add("unit", this.one(Unit.class, UnitTemplate.VALID));
				this.add("items", this.has(5).of(QuotationItem.class, QuotationItemTemplate.PRODUCT));
				this.add("discount", this.random(BigDecimal.class, this.range(BigDecimal.ZERO, new BigDecimal(100))));
			}
		});

		Fixture.of(Quotation.class).addTemplate(QuotationTemplate.SALE_WITH_ITEMS).inherits(QuotationTemplate.SALE, new Rule() {
			{
				this.add("unit", this.one(Unit.class, UnitTemplate.VALID));
				this.add("items", this.has(5).of(QuotationItem.class, QuotationItemTemplate.PRODUCT));
				this.add("discount", this.random(BigDecimal.class, this.range(BigDecimal.ZERO, new BigDecimal(100))));
			}
		});
	}

}
