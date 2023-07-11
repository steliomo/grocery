/**
 *
 */
package mz.co.grocery.persistence.customer.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class CustomerEntityMapper extends AbstractEntityMapper<CustomerEntity, Customer> implements EntityMapper<CustomerEntity, Customer> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	public CustomerEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper) {
		this.unitMapper = unitMapper;
	}

	@Override
	public CustomerEntity toEntity(final Customer domain) {
		final CustomerEntity entity = new CustomerEntity();
		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));

		entity.setName(domain.getName());
		entity.setAddress(domain.getAddress());
		entity.setContact(domain.getContact());
		entity.setEmail(domain.getEmail());

		return this.toEntity(entity, domain);
	}

	@Override
	public Customer toDomain(final CustomerEntity entity) {
		final Customer domain = new Customer();

		domain.setName(entity.getName());
		domain.setAddress(entity.getAddress());
		domain.setContact(entity.getContact());
		domain.setEmail(entity.getEmail());

		return this.toDomain(entity, domain);
	}
}
