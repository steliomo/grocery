/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author Stélio Moiane
 *
 */
public class SaleReport {

	private LocalDate saleDate;

	private final BigDecimal sale;

	private BigDecimal billing;

	private Month month;

	private BigDecimal profit;

	public SaleReport(final LocalDate saleDate, final BigDecimal billing, final BigDecimal sale) {
		this.saleDate = saleDate;
		this.billing = billing;
		this.sale = sale;
	}

	public SaleReport(final Month month) {
		this.month = month;
		this.sale = BigDecimal.ZERO;
		this.billing = BigDecimal.ZERO;
		this.profit = BigDecimal.ZERO;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public BigDecimal getBilling() {
		return this.billing;
	}

	public void setBilling(final BigDecimal billing) {
		this.billing = billing;
	}

	public BigDecimal getSale() {
		return this.sale;
	}

	public Month getMonth() {

		if (this.saleDate != null) {
			return this.saleDate.getMonth();
		}

		return this.month;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(final BigDecimal expense) {
		this.profit = this.billing.subtract(expense);
	}
}
