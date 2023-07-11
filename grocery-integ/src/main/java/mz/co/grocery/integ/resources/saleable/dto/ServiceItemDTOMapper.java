/**
 *
 */
package mz.co.grocery.integ.resources.saleable.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.core.domain.sale.ServiceItem;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.grocery.integ.resources.item.dto.ServiceDescriptionDTO;
import mz.co.grocery.integ.resources.unit.dto.UnitDTO;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ServiceItemDTOMapper extends AbstractDTOMapper<ServiceItemDTO, ServiceItem> implements DTOMapper<ServiceItemDTO, ServiceItem> {

	private DTOMapper<ServiceDescriptionDTO, ServiceDescription> serviceDescriptionMapper;
	private DTOMapper<UnitDTO, Unit> unitMapper;

	public ServiceItemDTOMapper(final DTOMapper<ServiceDescriptionDTO, ServiceDescription> serviceDescriptionMapper,
			final DTOMapper<UnitDTO, Unit> unitMapper) {
		this.serviceDescriptionMapper = serviceDescriptionMapper;
		this.unitMapper = unitMapper;
	}

	@Override
	public ServiceItemDTO toDTO(final ServiceItem domain) {
		final ServiceItemDTO dto = new ServiceItemDTO();

		domain.getServiceDescription()
		.ifPresent(serviceDescription -> dto.setServiceDescriptionDTO(this.serviceDescriptionMapper.toDTO(serviceDescription)));
		domain.getUnit().ifPresent(unit -> dto.setUnitDTO(this.unitMapper.toDTO(unit)));
		dto.setSalePrice(domain.getSalePrice());

		return this.toDTO(dto, domain);
	}

	@Override
	public ServiceItem toDomain(final ServiceItemDTO dto) {
		final ServiceItem domain = new ServiceItem();

		dto.getServiceDescriptionDTO()
		.ifPresent(serviceDescription -> domain.setServiceDescription(this.serviceDescriptionMapper.toDomain(serviceDescription)));
		dto.getUnitDTO().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		domain.setSalePrice(dto.getSalePrice());

		return this.toDomain(dto, domain);
	}
}
