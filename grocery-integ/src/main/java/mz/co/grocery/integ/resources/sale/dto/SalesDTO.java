/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import mz.co.grocery.core.expense.model.ExpenseReport;
import mz.co.grocery.core.grocery.model.GroceryUser;
import mz.co.grocery.core.grocery.model.UserRole;
import mz.co.grocery.core.sale.model.SaleReport;

/**
 * @author Stélio Moiane
 *
 */
public class SalesDTO {

	private List<SaleReport> salesReport;

	private List<ExpenseReport> expenses;

	private BigDecimal billing = BigDecimal.ZERO;

	private BigDecimal sales = BigDecimal.ZERO;

	private BigDecimal expense = BigDecimal.ZERO;

	private BigDecimal profit = BigDecimal.ZERO;

	private GroceryUser user;

	public SalesDTO() {
	}

	public SalesDTO(final GroceryUser user) {
		this.user = user;
	}

	public SalesDTO setSalesReport(final List<SaleReport> salesReport) {
		this.salesReport = salesReport;

		salesReport.forEach(saleReport -> {
			if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
				saleReport.setBilling(BigDecimal.ZERO);
			}

			this.billing = this.billing.add(saleReport.getBilling());
			this.sales = this.sales.add(saleReport.getSale());
		});

		return this;
	}

	public List<SaleReport> getSalesReport() {
		this.salesReport.sort((s1, s2) -> s1.getMonth().compareTo(s2.getMonth()));
		return this.salesReport;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public BigDecimal getSales() {
		return this.sales;
	}

	public SalesDTO setExpense(final BigDecimal expense) {
		this.expense = expense;
		this.profit = this.billing.subtract(this.expense);

		if (this.profit.doubleValue() < 0) {
			this.profit = BigDecimal.ZERO;
		}

		return this;
	}

	public BigDecimal getExpense() {

		if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
			return BigDecimal.ZERO;
		}

		return this.expense;
	}

	public BigDecimal getProfit() {

		if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
			return BigDecimal.ZERO;
		}

		return this.profit;
	}

	public SalesDTO addSalesMissingMonths() {

		for (final Month month : Month.values()) {

			final Optional<SaleReport> optional = this.salesReport.stream()
					.filter(saleReport -> month.equals(saleReport.getMonth())).findAny();

			if (!optional.isPresent()) {
				this.salesReport.add(new SaleReport(month));
			}
		}

		return this;
	}

	public SalesDTO addExpensesMissingMonths() {

		for (final Month month : Month.values()) {

			final Optional<ExpenseReport> optional = this.expenses.stream()
					.filter(expense -> month.equals(expense.getMonth())).findAny();

			if (!optional.isPresent()) {
				this.expenses.add(new ExpenseReport(month));
			}
		}

		return this;
	}

	public SalesDTO setExpenses(final List<ExpenseReport> expenses) {
		this.expenses = expenses;

		this.expense = expenses.stream().map(ExpenseReport::getExpenseValue).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.profit = this.billing.subtract(this.expense);

		return this;
	}

	public SalesDTO calculateMonthlyProfit() {

		this.expenses.forEach(expense -> {
			this.salesReport.stream().filter(saleReport -> expense.getMonth().equals(saleReport.getMonth())).findFirst()
			.get().setProfit(expense.getExpenseValue());
		});

		return this;
	}
}
