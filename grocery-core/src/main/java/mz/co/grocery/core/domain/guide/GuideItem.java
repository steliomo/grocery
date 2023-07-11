/**
 *
 */
package mz.co.grocery.core.domain.guide;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.sale.SaleItem;
import mz.co.grocery.core.domain.sale.Stock;

/**
 * @author Stélio Moiane
 *
 */

public class GuideItem extends Domain {

	private Guide guide;

	private RentItem rentItem;

	private SaleItem saleItem;

	private BigDecimal quantity;

	public Optional<Guide> getGuide() {
		return Optional.ofNullable(this.guide);
	}

	public void setGuide(final Guide guide) {
		this.guide = guide;
	}

	public Optional<RentItem> getRentItem() {
		return Optional.ofNullable(this.rentItem);
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

	public Optional<Stock> getStock() {
		return this.rentItem != null ? Optional.ofNullable((Stock) this.rentItem.getItem().get())
				: Optional.ofNullable(this.saleItem.getStock().get());
	}

	public Optional<SaleItem> getSaleItem() {
		return Optional.ofNullable(this.saleItem);
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
