/**
 *
 */
package mz.co.grocery.persistence.unit.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.domain.unit.UnitUser;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class UnitUserEntityMapper extends AbstractEntityMapper<UnitUserEntity, UnitUser> implements EntityMapper<UnitUserEntity, UnitUser> {

	private EntityMapper<UnitEntity, Unit> mapper;

	private EntityMapper<UnitEntity, Unit> unitEMapper;

	public UnitUserEntityMapper(final EntityMapper<UnitEntity, Unit> mapper, final EntityMapper<UnitEntity, Unit> unitEMapper) {
		this.mapper = mapper;
		this.unitEMapper = unitEMapper;
	}

	@Override
	public UnitUserEntity toEntity(final UnitUser domain) {
		final UnitUserEntity entity = new UnitUserEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitEMapper.toEntity(unit)));

		entity.setUser(domain.getUser());
		entity.setUserRole(domain.getUserRole());
		entity.setExpiryDate(domain.getExpiryDate());

		return this.toEntity(entity, domain);
	}

	@Override
	public UnitUser toDomain(final UnitUserEntity entity) {
		final UnitUser domain = new UnitUser();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.mapper.toDomain(unit)));

		domain.setUser(entity.getUser());
		domain.setUserRole(entity.getUserRole());
		domain.setExpiryDate(entity.getExpiryDate());

		return this.toDomain(entity, domain);
	}
}
