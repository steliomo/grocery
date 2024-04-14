/**
 *
 */
package mz.co.grocery.persistence.rent.entity;

import java.util.HashSet;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.Guide;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentItem;
import mz.co.grocery.core.domain.rent.RentPayment;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.common.AbstractEntityMapper;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.guide.entity.GuideEntity;
import mz.co.grocery.persistence.unit.entity.UnitEntity;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;

/**
 * @author Stélio Moiane
 *
 */
@Component
public class RentEntityMapper extends AbstractEntityMapper<RentEntity, Rent> implements EntityMapper<RentEntity, Rent> {

	private EntityMapper<UnitEntity, Unit> unitMapper;

	private EntityMapper<CustomerEntity, Customer> customerMapper;

	private EntityMapper<RentItemEntity, RentItem> rentItemMapper;

	private EntityMapper<RentPaymentEntity, RentPayment> rentPaymentMapper;

	private EntityMapper<GuideEntity, Guide> guideMapper;

	public RentEntityMapper(final EntityMapper<UnitEntity, Unit> unitMapper, final EntityMapper<CustomerEntity, Customer> customerMapper) {
		this.unitMapper = unitMapper;
		this.customerMapper = customerMapper;
	}

	@Override
	public RentEntity toEntity(final Rent domain) {
		final RentEntity entity = new RentEntity();

		domain.getUnit().ifPresent(unit -> entity.setUnit(this.unitMapper.toEntity(unit)));
		domain.getCustomer().ifPresent(customer -> entity.setCustomer(this.customerMapper.toEntity(customer)));

		entity.setRentDate(domain.getRentDate());
		entity.setPaymentStatus(domain.getPaymentStatus());
		entity.setLoadStatus(domain.getLoadStatus());
		entity.setReturnStatus(domain.getReturnStatus());
		entity.setTotalEstimated(domain.getTotalEstimated());
		entity.setTotalPaid(domain.getTotalPayment());
		entity.setTotalCalculated(domain.getTotalCalculated());
		entity.setRentStatus(domain.getRentStatus());

		return this.toEntity(entity, domain);
	}

	@Override
	public Rent toDomain(final RentEntity entity) {
		final Rent domain = new Rent();

		entity.getUnit().ifPresent(unit -> domain.setUnit(this.unitMapper.toDomain(unit)));

		domain.setRentDate(entity.getRentDate());
		entity.getCustomer().ifPresent(customer -> domain.setCustomer(this.customerMapper.toDomain(customer)));

		domain.setPaymentStatus(entity.getPaymentStatus());
		domain.setLoadStatus(entity.getLoadStatus());
		domain.setReturnStatus(entity.getReturnStatus());

		domain.setTotalEstimated(entity.getTotalEstimated());
		domain.setTotalPaid(entity.getTotalPaid());
		domain.setTotalCalculated(entity.getTotalCalculated());

		entity.getRentItems().ifPresent(rentItems -> rentItems.forEach(rentItem -> {
			rentItem.getRent().get().setRentItems(new HashSet<>());
			domain.addRentItem(this.rentItemMapper.toDomain(rentItem));
		}));

		entity.getRentPayments()
		.ifPresent(rentPayments -> rentPayments.forEach(rentPayment -> domain.addRentPayment(this.rentPaymentMapper.toDomain(rentPayment))));

		entity.getGuides().ifPresent(guides -> guides.forEach(guide -> {
			guide.getRent().get().setRentItems(new HashSet<>());
			domain.addGuide(this.guideMapper.toDomain(guide));
		}));

		return this.toDomain(entity, domain);
	}

	@Inject
	public void setRentItemMapper(final EntityMapper<RentItemEntity, RentItem> rentItemMapper) {
		this.rentItemMapper = rentItemMapper;
	}

	@Inject
	public void setRentPaymentMapper(final EntityMapper<RentPaymentEntity, RentPayment> rentPaymentMapper) {
		this.rentPaymentMapper = rentPaymentMapper;
	}

	@Inject
	public void setGuideMapper(final EntityMapper<GuideEntity, Guide> guideMapper) {
		this.guideMapper = guideMapper;
	}
}
