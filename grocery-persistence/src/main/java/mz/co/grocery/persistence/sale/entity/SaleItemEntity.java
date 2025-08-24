/**
 *
 */
package mz.co.grocery.persistence.sale.entity;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.domain.sale.DeliveryStatus;
import mz.co.grocery.persistence.sale.repository.SaleItemRepository;
import mz.co.msaude.boot.frameworks.model.GenericEntity;
import mz.co.msaude.boot.frameworks.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
@NamedQueries({
	@NamedQuery(name = SaleItemRepository.QUERY_NAME.findBySaleAndProductUuid, query = SaleItemRepository.QUERY.findBySaleAndProductUuid),
	@NamedQuery(name = SaleItemRepository.QUERY_NAME.findBySaleAndServiceUuid, query = SaleItemRepository.QUERY.findBySaleAndServiceUuid),
	@NamedQuery(name = SaleItemRepository.QUERY_NAME.findSaleItemsByUnitAndPeriod, query = SaleItemRepository.QUERY.findSaleItemsByUnitAndPeriod)
})
@Entity
@Table(name = "SALE_ITEMS")
public class SaleItemEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ID", nullable = false)
	private SaleEntity sale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private StockEntity stock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ITEM_ID")
	private ServiceItemEntity serviceItem;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	@NotNull
	@Column(name = "SALE_ITEM_VALUE", nullable = false)
	private BigDecimal saleItemValue;

	@NotNull
	@Column(name = "DISCOUNT", nullable = false)
	private BigDecimal discount;

	@NotNull
	@Column(name = "DELIVERED_QUANTITY", nullable = false)
	private BigDecimal deliveredQuantity;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "DELIVERY_STATUS", nullable = false)
	private DeliveryStatus deliveryStatus;

	public Optional<SaleEntity> getSale() {
		if (ProxyUtil.isProxy(this.sale)) {
			final SaleEntity sale = new SaleEntity();
			sale.setId(ProxyUtil.getIdentifier(this.sale));

			return Optional.of(sale);
		}

		return Optional.ofNullable(this.sale);
	}

	public void setSale(final SaleEntity sale) {
		this.sale = sale;
	}

	public Optional<StockEntity> getStock() {
		if (ProxyUtil.isProxy(this.stock)) {
			final StockEntity stock = new StockEntity();
			stock.setId(ProxyUtil.getIdentifier(this.stock));

			return Optional.of(stock);
		}

		return Optional.ofNullable(this.stock);
	}

	public void setStock(final StockEntity stock) {
		this.stock = stock;
	}

	public Optional<ServiceItemEntity> getServiceItem() {
		if (ProxyUtil.isProxy(this.serviceItem)) {
			final ServiceItemEntity serviceItem = new ServiceItemEntity();
			serviceItem.setId(ProxyUtil.getIdentifier(this.serviceItem));

			return Optional.of(serviceItem);
		}

		return Optional.ofNullable(this.serviceItem);
	}

	public void setServiceItem(final ServiceItemEntity serviceItem) {
		this.serviceItem = serviceItem;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
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

	public BigDecimal getDeliveredQuantity() {
		return this.deliveredQuantity;
	}

	public void setDeliveredQuantity(final BigDecimal deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}

	public DeliveryStatus getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(final DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
}
