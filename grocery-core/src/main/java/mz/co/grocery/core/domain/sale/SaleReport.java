/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import mz.co.grocery.core.application.sale.service.SalePeriodIdentifierUtil;
import mz.co.grocery.core.util.ApplicationTranslator;

/**
 * @author Stélio Moiane
 *
 */
public class SaleReport implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate saleDate;

	private BigDecimal sale;

	private BigDecimal billing;

	private Month month;

	private BigDecimal profit;

	private Long numberOfSales;

	private BigDecimal totalSales;

	private List<SalePeriodReport> salesPerPeriod;

	public SaleReport(final LocalDate saleDate, final BigDecimal billing, final BigDecimal sale) {
		this.saleDate = saleDate;
		this.billing = billing;
		this.sale = sale;
		this.profit = BigDecimal.ZERO;
	}

	public SaleReport(final Month month) {
		this.month = month;
		this.sale = BigDecimal.ZERO;
		this.billing = BigDecimal.ZERO;
		this.profit = BigDecimal.ZERO;
	}

	public SaleReport(final LocalDate saleDate, final Long numberOfSales, final BigDecimal totalSales) {
		this.saleDate = saleDate;
		this.numberOfSales = numberOfSales;
		this.totalSales = totalSales;
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

	public Long getNumberOfSales() {
		return this.numberOfSales;
	}

	public BigDecimal getTotalOfSales() {
		return this.totalSales;
	}

	public BigDecimal getAverageTicket() {
		if (this.totalSales == null) {
			return BigDecimal.ZERO;
		}

		return this.totalSales.divide(new BigDecimal(this.numberOfSales));
	}

	public void groupSalesPerPeriod(final List<Sale> sales, final ApplicationTranslator translator) {

		this.salesPerPeriod = new ArrayList<>();

		final SalePeriodReport morning = new SalePeriodReport(translator.getTranslation("sale.period.morning"), BigDecimal.ZERO.doubleValue());
		final SalePeriodReport afternoon = new SalePeriodReport(translator.getTranslation("sale.period.afternoon"), BigDecimal.ZERO.doubleValue());
		final SalePeriodReport night = new SalePeriodReport(translator.getTranslation("sale.period.night"), BigDecimal.ZERO.doubleValue());

		sales.forEach(sale -> {

			switch (SalePeriodIdentifierUtil.identify(sale.getCreatedAt().toLocalTime())) {

			case MORNING:
				morning.addValue(sale.getTotal().doubleValue());
				break;

			case AFTER_NOON:
				afternoon.addValue(sale.getTotal().doubleValue());
				break;

			default:
				night.addValue(sale.getTotal().doubleValue());
				break;
			}
		});

		this.salesPerPeriod.add(morning);
		this.salesPerPeriod.add(afternoon);
		this.salesPerPeriod.add(night);
	}

	public List<SalePeriodReport> getSalesPerPeriod() {
		return this.salesPerPeriod;
	}
}
