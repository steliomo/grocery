/**
 *
 */
package mz.co.grocery.integ.resources.item.dto;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.item.Service;
import mz.co.grocery.integ.resources.common.AbstractDTOMapper;
import mz.co.msaude.boot.frameworks.mapper.DTOMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ServiceDTOMapper extends AbstractDTOMapper<ServiceDTO, Service> implements DTOMapper<ServiceDTO, Service> {

	@Override
	public ServiceDTO toDTO(final Service domain) {
		final ServiceDTO dto = new ServiceDTO();
		dto.setName(domain.getName());
		return this.toDTO(dto, domain);
	}

	@Override
	public Service toDomain(final ServiceDTO dto) {
		final Service domain = new Service();
		domain.setName(dto.getName());
		return this.toDomain(dto, domain);
	}
}
