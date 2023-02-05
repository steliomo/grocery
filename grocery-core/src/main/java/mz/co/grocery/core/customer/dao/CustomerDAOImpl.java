/**
 *
 */
package mz.co.grocery.core.customer.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.customer.model.Customer;
import mz.co.grocery.core.rent.model.GuideType;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository(CustomerDAOImpl.NAME)
public class CustomerDAOImpl extends GenericDAOImpl<Customer, Long> implements CustomerDAO {

	public static final String NAME = "mz.co.grocery.core.customer.dao.CustomerDAOImpl";

	@Override
	public Long countByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(CustomerDAO.QUERY_NAME.countByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<Customer> findByUnit(final String unitUuid, final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(CustomerDAO.QUERY_NAME.findByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public List<Customer> findRentPendingPaymentsByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByQuery(CustomerDAO.QUERY_NAME.findRentPendingPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countPendingPaymentsByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(CustomerDAO.QUERY_NAME.countPendingPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<Customer> findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final int currentPage,
			final int maxResult,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(CustomerDAO.QUERY_NAME.findCustomersWithPendingOrIncompleteRentItemsToReturnByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findSingleByNamedQuery(CustomerDAO.QUERY_NAME.countCustomersWithPendingOrIncompleteRentItemsToReturnByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<Customer> findWithContractPendingPaymentByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final LocalDate currentDate,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(CustomerDAO.QUERY_NAME.findWithContractPendingPaymentByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("currentDate", currentDate).add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countCustomersWithContractPendingPaymentByUnit(final String unitUuid, final LocalDate currentDate, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findSingleByNamedQuery(CustomerDAO.QUERY_NAME.countCustomersWithContractPendingPaymentByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("currentDate", currentDate).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<Customer> findCustomersSaleWithPendindOrIncompletePaymentByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerDAO.QUERY_NAME.findCustomersSaleWithPendindOrIncompletePaymentByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Customer> findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit(final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerDAO.QUERY_NAME.findCustomersWithPendingOrInCompleteRentItemsToLoadByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Customer> findWithIssuedGuidesByTypeAndUnit(final GuideType guideType, final String unitUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(CustomerDAO.QUERY_NAME.findWithIssuedGuidesByTypeAndUnit,
				new ParamBuilder().add("guideType", guideType).add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Customer> findWithPaymentsByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(CustomerDAO.QUERY_NAME.findCustomersWithPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process());
	}
}
