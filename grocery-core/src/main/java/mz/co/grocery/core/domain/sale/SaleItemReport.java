/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemReport {

	private String name;

	private String quantity;

	private String salePrice;

	private String value;

	private BigDecimal total;

	private DecimalFormat formatter;

	public SaleItemReport(final String name, final BigDecimal quantity, final BigDecimal salePrice) {
		this.formatter = new DecimalFormat("#,###.00 MT");

		this.name = name.replace("0.00 NA", "");
		this.quantity = quantity.toString();
		this.salePrice = this.formatter.format(salePrice);
		this.total = salePrice.multiply(quantity);
		this.value = this.formatter.format(this.total);
	}

	public String getName() {
		return this.name;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public String getSalePrice() {
		return this.salePrice;
	}

	public String getValue() {
		return this.value;
	}

	public BigDecimal getTotal() {
		return this.total;
	}
}
