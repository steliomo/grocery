/**
 *
 */
package mz.co.grocery.core.domain.sale;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.core.common.BigDecimalUtil;
import mz.co.grocery.core.common.Domain;

/**
 * @author Stélio Moiane
 *
 */

public class SaleItem extends Domain {

	private Sale sale;

	private Stock stock;

	private ServiceItem serviceItem;

	private BigDecimal quantity;

	private BigDecimal saleItemValue;

	private BigDecimal discount;

	private BigDecimal deliveredQuantity;

	private DeliveryStatus deliveryStatus;

	public Optional<Sale> getSale() {
		return Optional.ofNullable(this.sale);
	}

	public void setSale(final Sale sale) {
		this.sale = sale;
	}

	public Optional<Stock> getStock() {
		return Optional.ofNullable(this.stock);
	}

	public void setStock(final Stock stock) {
		this.stock = stock;
	}

	public Optional<ServiceItem> getServiceItem() {
		return Optional.ofNullable(this.serviceItem);
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

	public void setDeliveredQuantity(final BigDecimal deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public void setDeliveryStatus(final DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
}
