/**
 *
 */
package mz.co.grocery.integ.resources.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@JsonInclude(Include.NON_NULL)
public abstract class GenericDTO<T extends GenericEntity> {

	private Long id;

	private final String uuid;

	public GenericDTO(final T t) {
		this.id = t.getId();
		this.uuid = t.getUuid();

		this.mapper(t);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public abstract void mapper(T t);
}
