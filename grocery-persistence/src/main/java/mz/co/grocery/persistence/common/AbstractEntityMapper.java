/**
 *
 */
package mz.co.grocery.persistence.common;

import mz.co.grocery.core.common.Domain;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
public abstract class AbstractEntityMapper<T extends GenericEntity, D extends Domain> {

	public T toEntity(final T entity, final D domain) {
		entity.setId(domain.getId());
		entity.setUuid(domain.getUuid());
		entity.setCreatedAt(domain.getCreatedAt());
		entity.setCreatedBy(domain.getCreatedBy());
		entity.setUpdatedAt(domain.getUpdatedAt());
		entity.setUpdatedBy(domain.getUpdatedBy());

		entity.setEntityStatus(domain.getEntityStatus());

		return entity;
	}

	public D toDomain(final T entity, final D domain) {
		domain.setId(entity.getId());
		domain.setUuid(entity.getUuid());
		domain.setCreatedAt(entity.getCreatedAt());
		domain.setCreatedBy(entity.getCreatedBy());
		domain.setUpdatedAt(entity.getUpdatedAt());
		domain.setUpdatedBy(entity.getUpdatedBy());

		domain.setEntityStatus(entity.getEntityStatus());

		return domain;
	}
}
