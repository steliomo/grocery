/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.item.Product;
import mz.co.grocery.core.domain.item.ProductDescription;
import mz.co.grocery.core.domain.item.ProductUnit;

/**
 * @author Stélio Moiane
 *
 */
public class ProductDescriptionTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(ProductDescription.class).addTemplate(VALID, new Rule() {
			{
				this.add("product", this.one(Product.class, ProductTemplate.VALID));
				this.add("description", "pipoca de milho");
				this.add("productUnit", this.one(ProductUnit.class, ProductUnitTemplate.VALID));
			}
		});

	}
}
