/**
 *
 */
package mz.co.grocery.integ.resources.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.GenericEntity;

/**
 * @author Stélio Moiane
 *
 */
@JsonInclude(Include.NON_NULL)
public abstract class GenericDTO<T extends GenericEntity> {

	private Long id;

	private String uuid;

	private String createdBy;

	private LocalDateTime createdAt;

	private EntityStatus entityStatus;

	public GenericDTO() {
	}

	public GenericDTO(final T t) {
		this.id = t.getId();
		this.uuid = t.getUuid();
		this.createdBy = t.getCreatedBy();
		this.createdAt = t.getCreatedAt();
		this.entityStatus = t.getEntityStatus();

		this.mapper(t);
	}

	public Long getId() {
		return this.id;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public EntityStatus getEntityStatus() {
		return this.entityStatus;
	}

	public abstract void mapper(T t);

	public T get(final T t) {
		t.setId(this.id);
		t.setUuid(this.uuid);
		t.setCreatedBy(this.createdBy);
		t.setCreatedAt(this.createdAt);
		t.setEntityStatus(this.entityStatus);

		return t;
	}

	public abstract T get();
}
