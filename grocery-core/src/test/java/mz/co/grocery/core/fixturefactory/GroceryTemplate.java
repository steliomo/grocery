/**
 *
 */
package mz.co.grocery.core.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.grocery.model.Grocery;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(Grocery.class).addTemplate(VALID, new Rule() {
			{
				this.add("name", "Mercearia Mafalda");
				this.add("address", "Matola-Gare, Matola, nr. 111");
				this.add("phoneNumber", "+258822546100");
				this.add("phoneNumberOptional", "+258840546824");
				this.add("email", "steliomo@gmail.com");
			}
		});
	}
}
