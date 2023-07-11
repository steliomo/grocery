/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.core.domain.item.ServiceDescription;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ServiceDescriptionDTOMapper extends AbstractDTOMapper<ServiceDescriptionDTO, ServiceDescription>
implements DTOMapper<ServiceDescriptionDTO, ServiceDescription> {

	private DTOMapper<ServiceDTO, Service> serviceMapper;

	public ServiceDescriptionDTOMapper(final DTOMapper<ServiceDTO, Service> serviceMapper) {
		this.serviceMapper = serviceMapper;
	}

	@Override
	public ServiceDescriptionDTO toDTO(final ServiceDescription domain) {
		final ServiceDescriptionDTO dto = new ServiceDescriptionDTO();
		domain.getService().ifPresent(service -> dto.setServiceDTO(this.serviceMapper.toDTO(service)));
		dto.setDescription(domain.getDescription());

		return this.toDTO(dto, domain);
	}

	@Override
	public ServiceDescription toDomain(final ServiceDescriptionDTO dto) {
		final ServiceDescription domain = new ServiceDescription();
		dto.getServiceDTO().ifPresent(service -> domain.setService(this.serviceMapper.toDomain(service)));
		domain.setDescription(dto.getDescription());
		return this.toDomain(dto, domain);
	}
}
