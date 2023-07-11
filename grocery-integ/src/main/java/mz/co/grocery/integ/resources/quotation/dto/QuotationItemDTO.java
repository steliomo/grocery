/**
 *
 */
package mz.co.grocery.integ.resources.quotation.dto;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.saleable.dto.ServiceItemDTO;
import mz.co.grocery.integ.resources.saleable.dto.StockDTO;

/**
 * @author Stélio Moiane
 *
 */
public class QuotationItemDTO extends GenericDTO {

	private QuotationDTO quotationDTO;

	private StockDTO stockDTO;

	private ServiceItemDTO serviceItemDTO;

	private BigDecimal quantity;

	private BigDecimal days;

	public void setQuotationDTO(final QuotationDTO quotationDTO) {
		this.quotationDTO = quotationDTO;
	}

	public Optional<QuotationDTO> getQuotationDTO() {
		return Optional.ofNullable(this.quotationDTO);
	}

	public Optional<StockDTO> getStockDTO() {
		return Optional.ofNullable(this.stockDTO);
	}

	public void setStockDTO(final StockDTO stockDTO) {
		this.stockDTO = stockDTO;
	}

	public void setServiceItemDTO(final ServiceItemDTO serviceItemDTO) {
		this.serviceItemDTO = serviceItemDTO;
	}

	public Optional<ServiceItemDTO> getServiceItemDTO() {
		return Optional.ofNullable(this.serviceItemDTO);
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
