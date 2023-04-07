/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.item.model.Product;
import mz.co.msaude.boot.frameworks.util.UuidFactory;

/**
 * @author Stélio Moiane
 *
 */
public class ProductTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(Product.class).addTemplate(VALID, new Rule() {
			{
				this.add("name", UuidFactory.generate());
			}
		});
	}
}
