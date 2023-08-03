/**
 *
 */
package mz.co.grocery.persistence.customer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.quotation.QuotationType;
import mz.co.grocery.persistence.customer.entity.CustomerEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class CustomerRepositoryImpl extends GenericDAOImpl<CustomerEntity, Long> implements CustomerRepository {

	@Override
	public Long countByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(CustomerRepository.QUERY_NAME.countByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<CustomerEntity> findByUnit(final String unitUuid, final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {

		return this.findByQuery(CustomerRepository.QUERY_NAME.findByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public List<CustomerEntity> findRentPendingPaymentsByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByQuery(CustomerRepository.QUERY_NAME.findRentPendingPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countPendingPaymentsByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(CustomerRepository.QUERY_NAME.countPendingPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<CustomerEntity> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final int currentPage,
			final int maxResult,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(CustomerRepository.QUERY_NAME.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findSingleByNamedQuery(CustomerRepository.QUERY_NAME.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<CustomerEntity> findWithContractPendingPaymentByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final LocalDate currentDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(CustomerRepository.QUERY_NAME.findWithContractPendingPaymentByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("currentDate", currentDate).add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countCustomersWithContractPendingPaymentByUnit(final String unitUuid, final LocalDate currentDate, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findSingleByNamedQuery(CustomerRepository.QUERY_NAME.countCustomersWithContractPendingPaymentByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("currentDate", currentDate).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<CustomerEntity> findCustomersSaleWithPendindOrIncompletePaymentByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerRepository.QUERY_NAME.findCustomersSaleWithPendindOrIncompletePaymentByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<CustomerEntity> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerRepository.QUERY_NAME.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<CustomerEntity> findWithIssuedGuidesByTypeAndUnit(final GuideType guideType, final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerRepository.QUERY_NAME.findWithIssuedGuidesByTypeAndUnit,
				new ParamBuilder().add("guideType", guideType).add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<CustomerEntity> findWithPaymentsByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(CustomerRepository.QUERY_NAME.findCustomersWithPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<CustomerEntity> findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerRepository.QUERY_NAME.findCustomersWithPendingOrIncompleteDeliveryStatusSalesByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<CustomerEntity> findCustomersWithDeliveredGuidesByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerRepository.QUERY_NAME.findCustomersWithDeliveredGuidesByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<CustomerEntity> findByUnitAndQuotationType(final String unitUuid, final EntityStatus entityStatus, final QuotationType type)
			throws BusinessException {
		return this.findByQuery(
				CustomerRepository.QUERY_NAME.findByUnitAndQuotationType,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).add("type", type).process()).setMaxResults(10)
				.getResultList();
	}
}
