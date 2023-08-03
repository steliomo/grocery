/**
 *
 */
package mz.co.grocery.integ.resources.dto;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import mz.co.msaude.boot.frameworks.model.EntityStatus;

/**
 * @author Stélio Moiane
 *
 */
public abstract class GenericDTO {

	protected static final int LEFT_PAD = 5;

	protected static final char PAD_CHAR = '0';

	private Long id;

	private String uuid;

	private String createdBy;

	private LocalDateTime createdAt;

	private String updatedBy;

	private LocalDateTime updatedAt;

	private EntityStatus entityStatus;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(final LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(final String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(final LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public EntityStatus getEntityStatus() {
		return this.entityStatus;
	}

	public void setEntityStatus(final EntityStatus entityStatus) {
		this.entityStatus = entityStatus;
	}

	public String getCode() {
		return StringUtils.leftPad(String.valueOf(this.id), GenericDTO.LEFT_PAD, GenericDTO.PAD_CHAR);
	}
}
