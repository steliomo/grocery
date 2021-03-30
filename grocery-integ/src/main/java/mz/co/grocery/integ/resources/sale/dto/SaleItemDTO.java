/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;

import mz.co.grocery.core.sale.model.SaleItem;
import mz.co.grocery.core.saleable.model.ServiceItem;
import mz.co.grocery.core.saleable.model.Stock;
import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;
import mz.co.grocery.integ.resources.util.ProxyUtil;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemDTO extends GenericDTO<SaleItem> {

	private StockDTO stockDTO;

	private ServiceItemDTO serviceItemDTO;

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
		final Stock stock = saleItem.getStock();

		if (ProxyUtil.isInitialized(stock)) {
			this.stockDTO = new StockDTO(stock);
		}

		final ServiceItem serviceItem = saleItem.getServiceItem();

		if (ProxyUtil.isInitialized(serviceItem)) {
			this.serviceItemDTO = new ServiceItemDTO(serviceItem);
		}

		this.quantity = saleItem.getQuantity();
		this.saleItemValue = saleItem.getSaleItemValue();
		this.discount = saleItem.getDiscount();
	}

	@Override
	public SaleItem get() {
		final SaleItem saleItem = this.get(new SaleItem());

		if (this.stockDTO != null) {
			saleItem.setStock(this.stockDTO.get());
		}

		if (this.serviceItemDTO != null) {
			saleItem.setServiceItem(this.serviceItemDTO.get());
		}

		saleItem.setQuantity(this.quantity);
		saleItem.setSaleItemValue(this.saleItemValue);
		saleItem.setDiscount(this.discount);

		return saleItem;
	}

	public StockDTO getStockDTO() {
		return this.stockDTO;
	}

	public ServiceItemDTO getServiceItemDTO() {
		return this.serviceItemDTO;
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
