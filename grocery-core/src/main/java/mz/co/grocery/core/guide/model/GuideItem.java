/**
 *
 */
package mz.co.grocery.core.guide.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import mz.co.grocery.core.rent.model.RentItem;
import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */

@Entity
@Table(name = "GUIDE_ITEMS")
public class GuideItem extends GenericEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GUIDE_ID", nullable = false)
	private Guide guide;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RENT_ITEM_ID")
	private RentItem rentItem;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_ITEM_ID")
	private SaleItem saleItem;

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	private BigDecimal quantity;

	public Guide getGuide() {
		return this.guide;
	}

	public void setGuide(final Guide guide) {
		this.guide = guide;
	}

	public RentItem getRentItem() {
		return this.rentItem;
	}

	public void setRentItem(final RentItem rentItem) {
		this.rentItem = rentItem;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Stock getStock() {
		return this.rentItem != null ? (Stock) this.rentItem.getItem() : this.saleItem.getStock();
	}

	public SaleItem getSaleItem() {
		return this.saleItem;
	}

	public void setSaleItem(final SaleItem saleItem) {
		this.saleItem = saleItem;
	}

	public GuideItemType getItemGuideType() {
		return this.rentItem == null ? GuideItemType.SALE : GuideItemType.RENT;
	}

	public String getName() {
		return this.rentItem != null ? this.rentItem.getName() : this.saleItem.getName();
	}
}
