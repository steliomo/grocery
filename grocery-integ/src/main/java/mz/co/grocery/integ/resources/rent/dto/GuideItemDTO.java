/**
 *
 */
package mz.co.grocery.integ.resources.rent.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.rent.model.GuideItem;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class GuideItemDTO extends GenericDTO<GuideItem> {

	private RentItemDTO rentItemDTO;

	private BigDecimal quantity;

	public GuideItemDTO() {
	}

	public GuideItemDTO(final GuideItem guideItem) {
		super(guideItem);
	}

	@Override
	public void mapper(final GuideItem guideItem) {

		if (ProxyUtil.isInitialized(guideItem.getRentItem())) {
			this.rentItemDTO = new RentItemDTO(guideItem.getRentItem());
		}

		this.quantity = guideItem.getQuantity();
	}

	@Override
	public GuideItem get() {
		final GuideItem guideItem = this.get(new GuideItem());
		guideItem.setRentItem(this.rentItemDTO.get());
		guideItem.setQuantity(this.quantity);

		return guideItem;
	}

	public RentItemDTO getRentItemDTO() {
		return this.rentItemDTO;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}
}
