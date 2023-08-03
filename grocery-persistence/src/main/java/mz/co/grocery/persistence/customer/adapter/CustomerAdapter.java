/**
 *
 */
package mz.co.grocery.persistence.customer.adapter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

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

	private CustomerRepository customerRepository;

	private EntityMapper<CustomerEntity, Customer> mapper;

	public CustomerAdapter(final CustomerRepository customerRepository,
			final EntityMapper<CustomerEntity, Customer> mapper) {
		this.customerRepository = customerRepository;
		this.mapper = mapper;
	}

	@Transactional
	@Override
	public Customer createCustomer(final UserContext userContext, final Customer customer) throws BusinessException {

		final CustomerEntity entity = this.mapper.toEntity(customer);

		this.customerRepository.create(userContext, entity);

		return this.mapper.toDomain(entity);
	}

	@Override
	public List<Customer> findCustomersByUnit(final String unitUuid, final int currentPage, final int maxResult) throws BusinessException {
		return this.customerRepository.findByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.countByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithRentPendingPeymentsByUnit(final String unitUuid, final int currentPage, final int maxResult)
			throws BusinessException {
		return this.customerRepository.findRentPendingPaymentsByUnit(unitUuid, currentPage, maxResult, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersWithPendingPeymentsByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.countPendingPaymentsByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final int currentPage,
			final int maxResult)
					throws BusinessException {
		return this.customerRepository.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, currentPage, maxResult,
				EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(unitUuid, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersWithContractPendingPaymentByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final LocalDate currentDate)
					throws BusinessException {
		return this.customerRepository.findWithContractPendingPaymentByUnit(unitUuid, currentPage, maxResult, currentDate, EntityStatus.ACTIVE)
				.stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Long countCustomersWithContractPendingPaymentByUnit(final String unitUuid, final LocalDate currentDate) throws BusinessException {
		return this.customerRepository.countCustomersWithContractPendingPaymentByUnit(unitUuid, currentDate, EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findCustomersSaleWithPendindOrIncompletePaymentByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.findCustomersSaleWithPendindOrIncompletePaymentByUnit(unitUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(unitUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithIssuedGuidesByTypeAndUnit(final GuideType guideType, final String unitUuid) throws BusinessException {
		return this.customerRepository.findWithIssuedGuidesByTypeAndUnit(guideType, unitUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithPaymentsByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.findWithPaymentsByUnit(unitUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(unitUuid, EntityStatus.ACTIVE).stream()
				.map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithDeliveredGuidesByUnit(final String unitUuid) throws BusinessException {
		return this.customerRepository.findCustomersWithDeliveredGuidesByUnit(unitUuid, EntityStatus.ACTIVE).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findCustomersWithQuotationByUnitAndType(final String unitUuid, final QuotationType quotationType) throws BusinessException {
		return this.customerRepository.findByUnitAndQuotationType(unitUuid, EntityStatus.ACTIVE, quotationType).stream().map(this.mapper::toDomain)
				.collect(Collectors.toList());
	}
}
