/**
 *
 */
package mz.co.grocery.core.domain.sale;

/**
 * @author Stélio Moiane
 *
 */
public class SalePeriodReport {

	private String period;

	private Double value;

	public SalePeriodReport(final String period, final Double value) {
		this.period = period;
		this.value = value;
	}

	public String getPeriod() {
		return this.period;
	}

	public void addValue(final Double value) {
		this.value = this.value + value;
	}

	public Double getValue() {
		return this.value;
	}
}
