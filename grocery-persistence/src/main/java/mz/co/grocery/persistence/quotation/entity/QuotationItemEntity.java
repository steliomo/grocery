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

import org.hibernate.LazyInitializationException;

import mz.co.grocery.persistence.sale.entity.ServiceItemEntity;
import mz.co.grocery.persistence.sale.entity.StockEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

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
		try {
			Optional.ofNullable(this.stock).ifPresent(stock -> stock.getQuantity());
			return Optional.ofNullable(this.stock);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
	}

	public void setStock(final StockEntity stock) {
		this.stock = stock;
	}

	public Optional<ServiceItemEntity> getServiceItem() {
		try {
			Optional.ofNullable(this.serviceItem).ifPresent(serviceItem -> serviceItem.getSalePrice());
			return Optional.ofNullable(this.serviceItem);
		} catch (final LazyInitializationException e) {
			return Optional.empty();
		}
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
