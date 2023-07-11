/**
 *
 */
package mz.co.grocery.persistence.fixturefactory;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.core.domain.unit.UserRole;
import mz.co.msaude.boot.frameworks.util.UuidFactory;

/**
 * @author Stélio Moiane
 *
 */
public class GroceryUserTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(UnitUser.class).addTemplate(GroceryUserTemplate.VALID, new Rule() {
			{
				this.add("unit", this.one(Unit.class, UnitTemplate.VALID));
				this.add("user", UuidFactory.generate());
				this.add("userRole", this.random(UserRole.ADMINISTRATOR, UserRole.MANAGER, UserRole.OPERATOR));
				this.add("expiryDate", LocalDate.now());
			}
		});
	}

}
