/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private List<SaleReport> salesBalance;

	private List<SaleReport> rentsBalance;

	private List<SaleDTO> salesDTO;

	public SalesDTO() {
	}

	public SalesDTO(final GroceryUser user) {
		this.user = user;
		this.salesBalance = new ArrayList<>();
		this.rentsBalance = new ArrayList<>();
	}

	public List<SaleReport> getSalesReport() {
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
		return this;
	}

	public BigDecimal getExpense() {

		if (this.user == null) {
			return BigDecimal.ZERO;
		}

		if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
			return BigDecimal.ZERO;
		}

		return this.expense;
	}

	public BigDecimal getProfit() {

		if (this.user == null) {
			return BigDecimal.ZERO;
		}

		if (UserRole.OPERATOR.equals(this.user.getUserRole()) || BigDecimal.ZERO.compareTo(this.profit) == BigDecimal.ONE.intValue()) {
			return BigDecimal.ZERO;
		}

		return this.profit;
	}

	public void addSalesMissingMonths() {

		for (final Month month : Month.values()) {

			final Optional<SaleReport> optional = this.salesReport.stream()
					.filter(saleReport -> month.equals(saleReport.getMonth())).findAny();

			if (!optional.isPresent()) {
				this.salesReport.add(new SaleReport(month));
			}
		}
	}

	public void addExpensesMissingMonths() {

		for (final Month month : Month.values()) {

			final Optional<ExpenseReport> optional = this.expenses.stream()
					.filter(expense -> month.equals(expense.getMonth())).findAny();

			if (!optional.isPresent()) {
				this.expenses.add(new ExpenseReport(month));
			}
		}
	}

	public SalesDTO setExpenses(final List<ExpenseReport> expenses) {
		this.expenses = expenses;
		return this;
	}

	public void calculateMonthlyProfit() {

		if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
			return;
		}

		this.expenses.forEach(expense -> {
			this.salesReport.stream().filter(saleReport -> expense.getMonth().equals(saleReport.getMonth())).findFirst()
			.get().setProfit(expense.getExpenseValue());
		});
	}

	public SalesDTO setSalesBalance(final List<SaleReport> salesBalance) {
		this.salesBalance = salesBalance;
		return this;
	}

	public SalesDTO setRentsBalance(final List<SaleReport> rentsBalance) {
		this.rentsBalance = rentsBalance;
		return this;
	}

	public SalesDTO buildPeriod() {

		this.salesReport = Stream.concat(this.salesBalance.stream(), this.rentsBalance.stream()).collect(Collectors.toList()).stream()
				.collect(Collectors.groupingBy(saleReport -> saleReport.getSaleDate())).entrySet().stream()
				.map(value -> value.getValue().stream().reduce((sale, rent) -> new SaleReport(sale.getSaleDate(),
						sale.getBilling().add(rent.getBilling()), sale.getSale().add(rent.getSale()))))
				.map(value -> value.get()).collect(Collectors.toList());

		this.salesReport.forEach(saleReport -> {
			if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
				saleReport.setBilling(BigDecimal.ZERO);
			}

			this.billing = this.billing.add(saleReport.getBilling());
			this.sales = this.sales.add(saleReport.getSale());
		});

		this.profit = this.billing.subtract(this.expense);

		if (this.profit.doubleValue() < 0) {
			this.profit = BigDecimal.ZERO;
		}

		this.salesReport.sort((s1, s2) -> s1.getSaleDate().compareTo(s2.getSaleDate()));

		return this;
	}

	public SalesDTO buildMonthly() {

		this.salesReport = Stream.concat(this.salesBalance.stream(), this.rentsBalance.stream()).collect(Collectors.toList()).stream()
				.collect(Collectors.groupingBy(saleReport -> saleReport.getMonth())).entrySet().stream()
				.map(value -> value.getValue().stream().reduce((sale, rent) -> new SaleReport(sale.getSaleDate(),
						sale.getBilling().add(rent.getBilling()), sale.getSale().add(rent.getSale()))))
				.map(value -> value.get()).collect(Collectors.toList());

		this.addSalesMissingMonths();

		this.addExpensesMissingMonths();

		this.calculateMonthlyProfit();

		this.salesReport.forEach(saleReport -> {
			if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
				saleReport.setBilling(BigDecimal.ZERO);
			}

			this.billing = this.billing.add(saleReport.getBilling());
			this.sales = this.sales.add(saleReport.getSale());
		});

		this.expense = this.expenses.stream().map(ExpenseReport::getExpenseValue).reduce(BigDecimal.ZERO, BigDecimal::add);

		if (UserRole.OPERATOR.equals(this.user.getUserRole())) {
			this.profit = BigDecimal.ZERO;
			return this;
		}

		this.profit = this.billing.subtract(this.expense);

		if (this.profit.doubleValue() < 0) {
			this.profit = BigDecimal.ZERO;
		}

		this.salesReport.sort((s1, s2) -> s1.getMonth().compareTo(s2.getMonth()));

		return this;
	}

	public List<SaleDTO> getSalesDTO() {
		return this.salesDTO;
	}

	public void addSale(final SaleDTO saleDTO) {
		if (this.salesDTO == null) {
			this.salesDTO = new ArrayList<>();
		}

		this.salesDTO.add(saleDTO);
	}
}
