/**
 *
 */
package mz.co.grocery.persistence.rent.entity;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */

@Component
public class RentPaymentEntityMapper extends AbstractEntityMapper<RentPaymentEntity, RentPayment>
implements EntityMapper<RentPaymentEntity, RentPayment> {

	private EntityMapper<RentEntity, Rent> rentMapper;

	@Override
	public RentPaymentEntity toEntity(final RentPayment domain) {

		final RentPaymentEntity entity = new RentPaymentEntity();

		domain.getRent().ifPresent(rent -> entity.setRent(this.rentMapper.toEntity(rent)));
		entity.setPaymentDate(domain.getPaymentDate());
		entity.setPaymentValue(domain.getPaymentValue());

		return this.toEntity(entity, domain);
	}

	@Override
	public RentPayment toDomain(final RentPaymentEntity entity) {
		final RentPayment domain = new RentPayment();

		entity.getRent().ifPresent(rent -> {
			rent.noRentPayment();
			domain.setRent(this.rentMapper.toDomain(rent));
		});

		domain.setPaymentDate(entity.getPaymentDate());
		domain.setPaymentValue(entity.getPaymentValue());

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setRentMapper(final EntityMapper<RentEntity, Rent> rentMapper) {
		this.rentMapper = rentMapper;
	}
}
