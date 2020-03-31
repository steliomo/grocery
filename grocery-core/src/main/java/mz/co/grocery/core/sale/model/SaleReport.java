/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Stélio Moiane
 *
 */
public class SaleReport {

	private final LocalDate saleDate;
	private final BigDecimal profit;
	private final BigDecimal sale;

	public SaleReport(final LocalDate saleDate, final BigDecimal profit, final BigDecimal sale) {
		this.saleDate = saleDate;
		this.profit = profit;
		this.sale = sale;
	}

	public LocalDate getSaleDate() {
		return this.saleDate;
	}

	public BigDecimal getProfit() {
		return this.profit;
	}

	public BigDecimal getSale() {
		return this.sale;
	}
}
