/**
 *
 */
package mz.co.grocery.persistence.rent.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import mz.co.grocery.core.application.rent.out.RentPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.Rent;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.rent.entity.RentEntity;
import mz.co.grocery.persistence.rent.repository.RentRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
@PersistenceAdapter
public class RentAdapter implements RentPort {

	private RentRepository repository;

	private EntityMapper<RentEntity, Rent> mapper;

	public RentAdapter(final RentRepository rentRepository, final EntityMapper<RentEntity, Rent> mapper) {
		this.repository = rentRepository;
		this.mapper = mapper;
	}

	@Override
	public List<Rent> findPendingPaymentRentsByCustomer(final String customerUuid) throws BusinessException {
		return this.repository.findPendinPaymentsByCustomer(customerUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Rent> fetchRentsWithPendingOrIncompleteRentItemToLoadByCustomer(final String customerUuid) throws BusinessException {
		return this.repository.fetchPendingOrIncompleteRentItemToLoadByCustomer(customerUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());
	}

	@Override
	public List<Rent> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(final String customerUuid) throws BusinessException {
		return this.repository.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(customerUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain).collect(Collectors.toList());

	}

	@Override
	public List<Rent> fetchRentsWithIssuedGuidesByTypeAndCustomer(final GuideType guideType, final String customerUuid) throws BusinessException {
		return this.repository.fetchWithIssuedGuidesByTypeAndCustomer(guideType, customerUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Rent> fetchRentsWithPaymentsByCustomer(final String customerUuid) throws BusinessException {
		return this.repository.fetchWithPaymentsByCustomer(customerUuid, EntityStatus.ACTIVE);
	}

	@Override
	public Rent fetchByUuid(final String uuid) throws BusinessException {
		return this.repository.fetchByUuid(uuid);
	}

	@Transactional
	@Override
	public Rent updateRent(final UserContext userContext, final Rent rent) throws BusinessException {

		final RentEntity entity = this.mapper.toEntity(rent);

		this.repository.update(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Rent findByUuid(final String uuid) throws BusinessException {
		return this.mapper.toDomain(this.repository.findByUuid(uuid));
	}

	@Transactional
	@Override
	public Rent createRent(final UserContext userContext, final Rent rent) throws BusinessException {

		final RentEntity entity = this.mapper.toEntity(rent);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public Optional<Rent> findRentByCustomerAndUnitAndStatus(final Customer customer, final Unit unit, final RentStatus rentStatus)
			throws BusinessException {

		final Optional<RentEntity> rentEntity = this.repository.findByCustomerAndUnitAndStatus(customer.getUuid(), unit.getUuid(), rentStatus,
				EntityStatus.ACTIVE);

		if (rentEntity.isPresent()) {
			return Optional.of(this.mapper.toDomain(rentEntity.get()));
		}

		return Optional.empty();
	}
}
