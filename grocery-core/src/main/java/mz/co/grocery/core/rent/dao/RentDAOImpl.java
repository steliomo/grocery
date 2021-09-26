/**
 *
 */
package mz.co.grocery.core.rent.dao;

import java.util.List;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.Rent;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository(RentDAOImpl.NAME)
public class RentDAOImpl extends GenericDAOImpl<Rent, Long> implements RentDAO {

	public static final String NAME = "mz.co.grocery.core.rent.dao.RentDAOImpl";

	@Override
	public List<Rent> findPendinPaymentsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(RentDAO.QUERY_NAME.findPendinPaymentsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), Rent.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public List<Rent> fetchPendingDevolutionsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(RentDAO.QUERY_NAME.fetchPendingDevolutionsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), Rent.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}
}
