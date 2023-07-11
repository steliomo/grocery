/**
 *
 */
package mz.co.grocery.persistence.quotation.entity;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.quotation.Quotation;
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
public class QuotationEntityMapper extends AbstractEntityMapper<QuotationEntity, Quotation> implements EntityMapper<QuotationEntity, Quotation> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<CustomerEntity, Customer> customerMapper;

	public QuotationEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper,
			final EntityMapper<CustomerEntity, Customer> customerMapper) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
	}

	@Override
	public QuotationEntity toEntity(final Quotation domain) {
		final QuotationEntity entity = new QuotationEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getCustomer().ifPresent(customer -> entity.setCustomer(this.customerMapper.toEntity(customer)));

		entity.setType(domain.getType());
		entity.setIssueDate(domain.getIssueDate());
		entity.setStatus(domain.getStatus());
		entity.setTotalValue(domain.getTotalValue());
		entity.setDiscount(domain.getDiscount());

		return this.toEntity(entity, domain);
	}

	@Override
	public Quotation toDomain(final QuotationEntity entity) {
		final Quotation domain = new Quotation(entity.getType(), entity.getStatus());

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));
		entity.getCustomer().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		domain.setType(entity.getType());
		domain.setIssueDate(entity.getIssueDate());
		domain.setStatus(entity.getStatus());
		domain.setTotalValue(entity.getTotalValue());
		domain.setDiscount(entity.getDiscount());

		return this.toDomain(entity, domain);
	}
}
