/**
 *
 */
package mz.co.grocery.integ.resources.guide.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.rent.dto.RentItemDTO;
import mz.co.grocery.integ.resources.sale.dto.SaleItemDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class GuideItemDTO extends GenericDTO<GuideItem> {

	private RentItemDTO rentItemDTO;

	private SaleItemDTO saleItemDTO;

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

		if (ProxyUtil.isInitialized(guideItem.getSaleItem())) {
			this.saleItemDTO = new SaleItemDTO(guideItem.getSaleItem());
		}

		this.quantity = guideItem.getQuantity();
	}

	@Override
	public GuideItem get() {
		final GuideItem guideItem = this.get(new GuideItem());

		if (this.rentItemDTO != null) {
			guideItem.setRentItem(this.rentItemDTO.get());
		}

		if (this.saleItemDTO != null) {
			guideItem.setSaleItem(this.saleItemDTO.get());
		}

		guideItem.setQuantity(this.quantity);

		return guideItem;
	}

	public RentItemDTO getRentItemDTO() {
		return this.rentItemDTO;
	}

	public SaleItemDTO getSaleItemDTO() {
		return this.saleItemDTO;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public String getName() {
		return this.rentItemDTO == null ? this.saleItemDTO.getName() : this.rentItemDTO.getName();
	}
}
