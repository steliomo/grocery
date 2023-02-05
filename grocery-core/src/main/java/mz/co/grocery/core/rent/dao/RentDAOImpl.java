/**
 *
 */
package mz.co.grocery.core.rent.dao;

import java.util.List;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.GuideType;
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

	private static final int MIN_RESULTS = 0;

	private static final int MAX_RESULTS = 30;

	public static final String NAME = "mz.co.grocery.core.rent.dao.RentDAOImpl";

	@Override
	public List<Rent> findPendinPaymentsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(RentDAO.QUERY_NAME.findPendinPaymentsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), Rent.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public List<Rent> fetchPendingOrIncompleteRentItemToLoadByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(RentDAO.QUERY_NAME.fetchPendingOrIncompleteRentItemToLoadByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), Rent.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public List<Rent> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(RentDAO.QUERY_NAME.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), Rent.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public Rent fetchByUuid(final String uuid) throws BusinessException {
		return this.findSingleByNamedQuery(RentDAO.QUERY_NAME.fetchByUuid, new ParamBuilder().add("uuid", uuid).process());
	}

	@Override
	public List<Rent> fetchWithIssuedGuidesByTypeAndCustomer(final GuideType guideType, final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByNamedQuery(RentDAO.QUERY_NAME.fetchRentsWithIssuedGuidesByTypeAndCustomer,
				new ParamBuilder().add("guideType", guideType).add("customerUuid", customerUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Rent> fetchWithPaymentsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(RentDAO.QUERY_NAME.fetchRentsWithPaymentsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).setFirstResult(RentDAOImpl.MIN_RESULTS).setMaxResults(RentDAOImpl.MAX_RESULTS)
				.getResultList();
	}
}
