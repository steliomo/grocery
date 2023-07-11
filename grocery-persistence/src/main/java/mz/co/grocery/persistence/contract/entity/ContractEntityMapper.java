/**
 *
 */
package mz.co.grocery.persistence.contract.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.contract.Contract;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class ContractEntityMapper extends AbstractEntityMapper<ContractEntity, Contract> implements EntityMapper<ContractEntity, Contract> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<CustomerEntity, Customer> customerMapper;

	public ContractEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper, final EntityMapper<CustomerEntity, Customer> customerMapper) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
	}

	@Override
	public ContractEntity toEntity(final Contract domain) {
		final ContractEntity entity = new ContractEntity();

		entity.setContractType(domain.getContractType());
		entity.setDescription(domain.getDescription());
		entity.setStartDate(domain.getStartDate());
		entity.setEndDate(domain.getEndDate());
		entity.setReferencePaymentDate(domain.getReferencePaymentDate());
		entity.setMonthlyPayment(domain.getMonthlyPayment());
		entity.setTotalPaid(domain.getTotalPaid());

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getCustomer().ifPresent(customer -> entity.setCustomer(this.customerMapper.toEntity(customer)));

		return this.toEntity(entity, domain);
	}

	@Override
	public Contract toDomain(final ContractEntity entity) {
		final Contract domain = new Contract();

		domain.setId(entity.getId());
		domain.setUuid(entity.getUuid());

		domain.setContractType(entity.getContractType());
		domain.setDescription(entity.getDescription());
		domain.setStartDate(entity.getStartDate());
		domain.setEndDate(entity.getEndDate());
		domain.setReferencePaymentDate(entity.getReferencePaymentDate());
		domain.setMonthlyPayment(entity.getMonthlyPayment());
		domain.setTotalPaid(entity.getTotalPaid());

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		entity.getCustomer().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		return this.toDomain(entity, domain);
	}
}
