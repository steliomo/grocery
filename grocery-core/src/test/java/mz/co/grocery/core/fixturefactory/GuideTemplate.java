/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import java.time.LocalDate;
import java.util.HashSet;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.rent.model.Rent;
import mz.co.grocery.core.sale.model.Sale;

/**
 * @author Stélio Moiane
 *
 */
public class GuideTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	public static final String TRANSPORT = "TRANSPORT";

	public static final String NO_ITEMS_TRANSPORT = "NO_ITEMS";

	public static final String RETURN = "RETURN";

	public static final String NO_ITEMS_RETURN = "NO_ITEMS_RETURN";

	public static final String DELIVERY = "DELIVERY";

	@Override
	public void load() {

		Fixture.of(Guide.class).addTemplate(GuideTemplate.VALID, new Rule() {
			{
				this.add("rent", this.one(Rent.class, RentTemplate.VALID));
				this.add("issueDate", LocalDate.now());
			}
		});

		Fixture.of(Guide.class).addTemplate(GuideTemplate.TRANSPORT).inherits(GuideTemplate.VALID, new Rule() {
			{
				this.add("type", GuideType.TRANSPORT);
			}
		});

		Fixture.of(Guide.class).addTemplate(GuideTemplate.RETURN).inherits(GuideTemplate.VALID, new Rule() {
			{
				this.add("type", GuideType.RETURN);
			}
		});

		Fixture.of(Guide.class).addTemplate(GuideTemplate.NO_ITEMS_TRANSPORT).inherits(GuideTemplate.TRANSPORT, new Rule() {
			{
				this.add("guideItems", new HashSet<GuideItem>());
			}
		});

		Fixture.of(Guide.class).addTemplate(GuideTemplate.NO_ITEMS_RETURN).inherits(GuideTemplate.RETURN, new Rule() {
			{
				this.add("guideItems", new HashSet<GuideItem>());
			}
		});

		Fixture.of(Guide.class).addTemplate(GuideTemplate.DELIVERY, new Rule() {
			{
				this.add("type", GuideType.DELIVERY);
				this.add("sale", this.one(Sale.class, SaleTemplate.VALID));
				this.add("guideItems", new HashSet<GuideItem>());
			}
		});
	}

}
