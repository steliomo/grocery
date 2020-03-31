/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.stock.dto.StockDTO;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemDTO extends GenericDTO<SaleItem> {

	private StockDTO stockDTO;

	private BigDecimal quantity;

	private BigDecimal saleItemValue = BigDecimal.ZERO;

	private BigDecimal discount = BigDecimal.ZERO;

	public SaleItemDTO() {
	}

	public SaleItemDTO(final SaleItem saleItem) {
		super(saleItem);
		this.mapper(saleItem);
	}

	@Override
	public void mapper(final SaleItem saleItem) {
		this.stockDTO = new StockDTO(saleItem.getStock());
		this.quantity = saleItem.getQuantity();
		this.saleItemValue = saleItem.getSaleItemValue();
		this.discount = saleItem.getDiscount();
	}

	@Override
	public SaleItem get() {
		final SaleItem saleItem = this.get(new SaleItem());
		saleItem.setStock(this.stockDTO.get());
		saleItem.setQuantity(this.quantity);
		saleItem.setSaleItemValue(this.saleItemValue);
		saleItem.setDiscount(this.discount);

		return saleItem;
	}

	public StockDTO getStockDTO() {
		return this.stockDTO;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public BigDecimal getSaleItemValue() {
		return this.saleItemValue;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}
}
