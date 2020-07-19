package mz.co.grocery.core.fixturefactory;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.grocery.core.expense.model.ExpenseTypeCategory;

public class ExpenseTypeTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {

		Fixture.of(ExpenseType.class).addTemplate(ExpenseTypeTemplate.VALID, new Rule() {
			{
				this.add("expenseTypeCategory", ExpenseTypeCategory.EXPENSE);
				this.add("name", "Agua");
				this.add("description", "Agua que o estabelecimento consome");
			}
		});
	}

}
