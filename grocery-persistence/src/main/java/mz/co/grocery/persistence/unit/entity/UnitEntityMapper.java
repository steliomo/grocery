/**
 *
 */
package mz.co.grocery.persistence.unit.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class UnitEntityMapper extends AbstractEntityMapper<UnitEntity, Unit> implements EntityMapper<UnitEntity, Unit> {

	@Override
	public UnitEntity toEntity(final Unit domain) {
		final UnitEntity entity = new UnitEntity();

		entity.setName(domain.getName());
		entity.setAddress(domain.getAddress());
		entity.setPhoneNumber(domain.getPhoneNumber());
		entity.setPhoneNumberOptional(domain.getPhoneNumberOptional());
		entity.setEmail(domain.getEmail());
		entity.setUnitType(domain.getUnitType());
		entity.setBalance(domain.getBalance());
		entity.setNumberOfTables(domain.getNumberOfTables());
		entity.setLogoPath(domain.getLogoPath());
		entity.setSignaturePath(domain.getSignaturePath());

		return this.toEntity(entity, domain);
	}

	@Override
	public Unit toDomain(final UnitEntity entity) {
		final Unit domain = new Unit();

		domain.setName(entity.getName());
		domain.setAddress(entity.getAddress());
		domain.setPhoneNumber(entity.getPhoneNumber());
		domain.setPhoneNumberOptional(entity.getPhoneNumberOptional());
		domain.setEmail(entity.getEmail());
		domain.setUnitType(entity.getUnitType());
		domain.setBalance(entity.getBalance());
		domain.setNumberOfTables(entity.getNumberOfTables());
		domain.setLogoPath(entity.getLogoPath());
		domain.setSignaturePath(entity.getSignaturePath());

		return this.toDomain(entity, domain);
	}
}
