/**
 *
 */
package mz.co.grocery.integ.resources.guide.dto;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.rent.dto.RentItemDTO;
import mz.co.grocery.integ.resources.sale.dto.SaleItemDTO;

/**
 * @author Stélio Moiane
 *
 */
public class GuideItemDTO extends GenericDTO {

	private RentItemDTO rentItemDTO;

	private SaleItemDTO saleItemDTO;

	private BigDecimal quantity;

	public Optional<RentItemDTO> getRentItemDTO() {
		return Optional.ofNullable(this.rentItemDTO);
	}

	public void setRentItemDTO(final RentItemDTO rentItemDTO) {
		this.rentItemDTO = rentItemDTO;
	}

	public Optional<SaleItemDTO> getSaleItemDTO() {
		return Optional.ofNullable(this.saleItemDTO);
	}

	public void setSaleItemDTO(final SaleItemDTO saleItemDTO) {
		this.saleItemDTO = saleItemDTO;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return this.rentItemDTO == null ? this.saleItemDTO.getName() : this.rentItemDTO.getName();
	}
}
