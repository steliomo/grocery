/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import mz.co.grocery.core.stock.model.Stock;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@Entity
@Table(name = "SALE_ITEMS")
public class SaleItem extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@XmlTransient
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID", nullable = false)
	private Sale sale;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private Stock stock;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	@NotNull
	@Column(name = "SALE_ITEM_VALUE", nullable = false)
	private BigDecimal saleItemValue = BigDecimal.ZERO;

	@NotNull
	@Column(name = "DISCOUNT", nullable = false)
	private BigDecimal discount = BigDecimal.ZERO;

	public Sale getSale() {
		return this.sale;
	}

	public void setSale(final Sale sale) {
		this.sale = sale;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(final Stock stock) {
		this.stock = stock;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalSaleItem() {
		return this.saleItemValue.subtract(this.discount);
	}

	public BigDecimal getSaleItemValue() {
		return this.saleItemValue;
	}

	public void setSaleItemValue(final BigDecimal saleItemValue) {
		this.saleItemValue = saleItemValue;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(final BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTotalBilling() {
		return this.stock.getSalePrice().subtract(this.stock.getPurchasePrice()).multiply(this.quantity)
				.subtract(this.discount);
	}
}
