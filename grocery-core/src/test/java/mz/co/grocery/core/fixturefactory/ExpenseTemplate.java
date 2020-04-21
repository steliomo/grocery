package mz.co.grocery.core.fixturefactory;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import mz.co.grocery.core.expense.model.Expense;
import mz.co.grocery.core.expense.model.ExpenseType;
import mz.co.grocery.core.grocery.model.Grocery;

public class ExpenseTemplate implements TemplateLoader {

	public static final String VALID = "VALID";

	@Override
	public void load() {
		Fixture.of(Expense.class).addTemplate(ExpenseTemplate.VALID, new Rule() {
			{
				this.add("grocery", this.one(Grocery.class, GroceryTemplate.VALID));
				this.add("expenseType", this.one(ExpenseType.class, ExpenseTypeTemplate.VALID));
				this.add("datePerformed", LocalDate.now());
				this.add("expenseValue", this.random(BigDecimal.class, this.range(1000, 4000)));
				this.add("description", "saida de valores");
			}
		});

	}
}
