/**
 *
 */
package mz.co.grocery.core.customer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.customer.model.Customer;
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
	public List<Customer> findPendingPaymentsByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByQuery(CustomerDAO.QUERY_NAME.findPendingPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countPendingPaymentsByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(CustomerDAO.QUERY_NAME.countPendingPaymentsByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}

	@Override
	public List<Customer> findPendingDevolutionByUnit(final String unitUuid, final int currentPage, final int maxResult,
			final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(CustomerDAO.QUERY_NAME.findPendingDevolutionByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process()).setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public Long countPendingDevolutionByUnit(final String unitUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findSingleByNamedQuery(CustomerDAO.QUERY_NAME.countPendingDevolutionByUnit,
				new ParamBuilder().add("unitUuid", unitUuid).add("entityStatus", entityStatus).process(), Long.class);
	}
}
