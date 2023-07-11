/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import java.math.BigDecimal;
import java.util.Optional;

import mz.co.grocery.integ.resources.dto.GenericDTO;
import mz.co.grocery.integ.resources.item.dto.ServiceDescriptionDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;

/**
 * @author Stélio Moiane
 *
 */
public class ServiceItemDTO extends GenericDTO {

	private ServiceDescriptionDTO serviceDescriptionDTO;

	private UnitDTO unitDTO;

	private BigDecimal salePrice;

	public Optional<ServiceDescriptionDTO> getServiceDescriptionDTO() {
		return Optional.ofNullable(this.serviceDescriptionDTO);
	}

	public void setServiceDescriptionDTO(final ServiceDescriptionDTO serviceDescriptionDTO) {
		this.serviceDescriptionDTO = serviceDescriptionDTO;
	}

	public Optional<UnitDTO> getUnitDTO() {
		return Optional.ofNullable(this.unitDTO);
	}

	public void setUnitDTO(final UnitDTO unitDTO) {
		this.unitDTO = unitDTO;
	}

	public BigDecimal getSalePrice() {
		return this.salePrice;
	}

	public void setSalePrice(final BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getName() {
		return this.serviceDescriptionDTO.getName();
	}
}
