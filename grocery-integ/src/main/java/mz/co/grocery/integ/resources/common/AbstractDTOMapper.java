/**
 *
 */
package mz.co.grocery.integ.resources.common;

import mz.co.grocery.core.common.Domain;
import mz.co.grocery.integ.resources.dto.GenericDTO;

/**
 * @author Stélio Moiane
 *
 */
public abstract class AbstractDTOMapper<T extends GenericDTO, D extends Domain> {

	public T toDTO(final T dto, final D domain) {

		dto.setId(domain.getId());
		dto.setUuid(domain.getUuid());
		dto.setCreatedAt(domain.getCreatedAt());
		dto.setCreatedBy(domain.getCreatedBy());
		dto.setUpdatedAt(domain.getUpdatedAt());
		dto.setUpdatedBy(domain.getUpdatedBy());

		dto.setEntityStatus(domain.getEntityStatus());

		return dto;
	}

	public D toDomain(final T dto, final D domain) {

		domain.setId(dto.getId());
		domain.setUuid(dto.getUuid());
		domain.setCreatedAt(dto.getCreatedAt());
		domain.setCreatedBy(dto.getCreatedBy());
		domain.setUpdatedAt(dto.getUpdatedAt());
		domain.setUpdatedBy(dto.getUpdatedBy());

		domain.setEntityStatus(dto.getEntityStatus());

		return domain;
	}
}
