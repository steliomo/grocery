/**
 *
 */
package mz.co.grocery.persistence.quotation.entity;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;
import mz.co.msaude.boot.frameworks.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */

@Entity
@Table(name = "QUOTATION_ITEMS")
public class QuotationItemEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUOTATION_ID", nullable = false)
	private QuotationEntity quotation;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	private StockEntity stock;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICE_ITEM_ID")
	private ServiceItemEntity serviceItem;

	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	@Column(name = "DAYS")
	private BigDecimal days;

	public Optional<QuotationEntity> getQuotation() {
		return Optional.ofNullable(this.quotation);
	}

	public void setQuotation(final QuotationEntity quotation) {
		this.quotation = quotation;
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

	public BigDecimal getDays() {
		return this.days;
	}

	public void setDays(final BigDecimal days) {
		this.days = days;
	}
}
