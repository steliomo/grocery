/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.rent.model.GuideItem;
import mz.co.grocery.core.rent.model.RentItem;

/**
 * @author Stélio Moiane
 *
 */
public class GuideItemTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(GuideItem.class).addTemplate(GuideItemTemplate.VALID, new Rule() {
			{
				this.add("rentItem", this.one(RentItem.class, RentItemTemplate.PRODUCT));
				this.add("quantity", this.random(BigDecimal.class, this.range(1, 5)));
			}
		});
	}

}
