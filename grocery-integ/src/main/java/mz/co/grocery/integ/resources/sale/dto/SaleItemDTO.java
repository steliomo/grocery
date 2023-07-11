/**
 *
 */
package mz.co.grocery.integ.resources.sale.dto;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;

/**
 * @author Stélio Moiane
 *
 */
public class SaleItemDTO extends GenericDTO {

	private StockDTO stockDTO;

	private ServiceItemDTO serviceItemDTO;

	private BigDecimal quantity;

	private BigDecimal saleItemValue;

	private BigDecimal discount;

	private BigDecimal deliveredQuantity;

	public SaleItemDTO() {
		this.saleItemValue = BigDecimal.ZERO;
		this.discount = BigDecimal.ZERO;
		this.deliveredQuantity = BigDecimal.ZERO;
	}

	public Optional<StockDTO> getStockDTO() {
		return Optional.ofNullable(this.stockDTO);
	}

	public void setStockDTO(final StockDTO stockDTO) {
		this.stockDTO = stockDTO;
	}

	public Optional<ServiceItemDTO> getServiceItemDTO() {
		return Optional.ofNullable(this.serviceItemDTO);
	}

	public void setServiceItemDTO(final ServiceItemDTO serviceItemDTO) {
		this.serviceItemDTO = serviceItemDTO;
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

	public String getName() {
		return this.stockDTO == null ? this.serviceItemDTO.getName() : this.stockDTO.getProductDescriptionDTO().get().getName();
	}
}
