/**
 *
 */
package mz.co.grocery.core.sale.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.core.util.BigDecimalUtil;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private Stock stock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ITEM_ID")
	private ServiceItem serviceItem;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	@NotNull
	@Column(name = "SALE_ITEM_VALUE", nullable = false)
	private BigDecimal saleItemValue = BigDecimal.ZERO;

	@NotNull
	@Column(name = "DISCOUNT", nullable = false)
	private BigDecimal discount = BigDecimal.ZERO;

	@NotNull
	@Column(name = "DELIVERED_QUANTITY", nullable = false)
	private BigDecimal deliveredQuantity = BigDecimal.ZERO;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "DELIVERY_STATUS", nullable = false)
	private DeliveryStatus deliveryStatus = DeliveryStatus.NA;

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

	public ServiceItem getServiceItem() {
		return this.serviceItem;
	}

	public void setServiceItem(final ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
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

		if (this.isProduct()) {
			return this.stock.getSalePrice().subtract(this.stock.getPurchasePrice()).multiply(this.quantity).subtract(this.discount);
		}

		return this.serviceItem.getSalePrice().multiply(this.quantity).subtract(this.discount);
	}

	public Boolean isProduct() {
		return this.stock != null;
	}

	public void addDeliveredQuantity(final BigDecimal quantity) {
		this.deliveredQuantity = this.deliveredQuantity.add(quantity);
	}

	public BigDecimal getDeliveredQuantity() {
		return this.deliveredQuantity;
	}

	public void setDeliveryStatus() {

		if (BigDecimalUtil.isZero(this.deliveredQuantity)) {
			this.deliveryStatus = DeliveryStatus.PENDING;
			return;
		}

		if (BigDecimalUtil.isEqual(this.quantity, this.deliveredQuantity)) {
			this.deliveryStatus = DeliveryStatus.COMPLETE;
			return;
		}

		this.deliveryStatus = DeliveryStatus.INCOMPLETE;
	}

	public DeliveryStatus getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public String getName() {
		return this.stock != null ? this.stock.getName() : this.serviceItem.getName();
	}

	public BigDecimal getQuantityToDeliver() {
		return this.quantity.subtract(this.deliveredQuantity);
	}
}
