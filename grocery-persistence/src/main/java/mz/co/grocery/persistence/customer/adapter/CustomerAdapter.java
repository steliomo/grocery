/**
 *
 */
package mz.co.grocery.persistence.customer.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import mz.co.grocery.core.application.customer.out.CustomerPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.customer.Customer;
import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.grocery.persistence.customer.repository.CustomerRepository;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.mapper.EntityMapper;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class CustomerAdapter implements CustomerPort {

	private CustomerRepository repository;

	private EntityMapper<CustomerEntity, Customer> mapper;

	public CustomerAdapter(final CustomerRepository customerRepository,
			final EntityMapper<CustomerEntity, Customer> mapper) {
		this.repository = customerRepository;
		this.mapper = mapper;
	}


	@Override
	public Customer createCustomer(final UserContext userContext, final Customer customer) throws BusinessException {

		final CustomerEntity entity = this.mapper.toEntity(customer);

		this.repository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<Customer> findCustomersByUnit(final String unitUuid, final int currentPage, final int maxResult) throws BusinessException {
		return this.repository.findByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersByUnit(final String unitUuid) throws BusinessException {
		return this.repository.countByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithRentPendingPeymentsByUnit(final String unitUuid, final int currentPage, final int maxResult)
			throws BusinessException {
		return this.repository.findRentPendingPaymentsByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersWithPendingPeymentsByUnit(final String unitUuid) throws BusinessException {
		return this.repository.countPendingPaymentsByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final int currentPage,
			final int maxResult)
					throws BusinessException {
		return this.repository.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, currentPage, maxResult,
				EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid) throws BusinessException {
		return this.repository.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithContractPendingPaymentByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final LocalDate currentDate)
					throws BusinessException {
		return this.repository.findWithContractPendingPaymentByUnit(unitUuid, currentPage, maxResult, currentDate, EntityStatus.ACTIVE)
				.stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersWithContractPendingPaymentByUnit(final String unitUuid, final LocalDate currentDate) throws BusinessException {
		return this.repository.countCustomersWithContractPendingPaymentByUnit(unitUuid, currentDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersSaleWithPendindOrIncompletePaymentByUnit(final String unitUuid) throws BusinessException {
		return this.repository.findCustomersSaleWithPendindOrIncompletePaymentByUnit(unitUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(final String unitUuid) throws BusinessException {
		return this.repository.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(unitUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithIssuedGuidesByTypeAndUnit(final GuideType guideType, final String unitUuid) throws BusinessException {
		return this.repository.findWithIssuedGuidesByTypeAndUnit(guideType, unitUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithPaymentsByUnit(final String unitUuid) throws BusinessException {
		return this.repository.findWithPaymentsByUnit(unitUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(final String unitUuid) throws BusinessException {
		return this.repository.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(unitUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithDeliveredGuidesByUnit(final String unitUuid) throws BusinessException {
		return this.repository.findCustomersWithDeliveredGuidesByUnit(unitUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithQuotationByUnitAndType(final String unitUuid, final QuotationType quotationType) throws BusinessException {
		return this.repository.findByUnitAndQuotationType(unitUuid, EntityStatus.ACTIVE, quotationType).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Customer> findCustomerByContact(final String contact) throws BusinessException {

		final Optional<CustomerEntity> foundCustomer = this.repository.findCustomerByContact(contact, EntityStatus.ACTIVE);

		if (foundCustomer.isPresent()) {
			return Optional.of(this.mapper.toDomain(foundCustomer.get()));
		}

		return Optional.empty();
	}
}
