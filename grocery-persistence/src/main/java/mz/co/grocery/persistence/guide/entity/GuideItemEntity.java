/**
 *
 */
package mz.co.grocery.persistence.guide.entity;

import java.math.BigDecimal;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.persistence.rent.entity.RentItemEntity;
import mz.co.grocery.persistence.sale.entity.SaleItemEntity;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@Entity
@Table(name = "GUIDE_ITEMS")
public class GuideItemEntity extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GUIDE_ID", nullable = false)
	private GuideEntity guide;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ITEM_ID")
	private RentItemEntity rentItem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ITEM_ID")
	private SaleItemEntity saleItem;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	public Optional<GuideEntity> getGuide() {
		return Optional.ofNullable(this.guide);
	}

	public void setGuide(final GuideEntity guide) {
		this.guide = guide;
	}

	public Optional<RentItemEntity> getRentItem() {
		return Optional.ofNullable(this.rentItem);
	}

	public void setRentItem(final RentItemEntity rentItem) {
		this.rentItem = rentItem;
	}

	public Optional<SaleItemEntity> getSaleItem() {
		return Optional.ofNullable(this.saleItem);
	}

	public void setSaleItem(final SaleItemEntity saleItem) {
		this.saleItem = saleItem;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}
}
