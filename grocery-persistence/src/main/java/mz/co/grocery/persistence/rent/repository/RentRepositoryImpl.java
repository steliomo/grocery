/**
 *
 */
package mz.co.grocery.persistence.rent.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import mz.co.grocery.core.domain.guide.GuideType;
import mz.co.grocery.core.domain.rent.RentStatus;
import mz.co.grocery.persistence.rent.entity.RentEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class RentRepositoryImpl extends GenericDAOImpl<RentEntity, Long> implements RentRepository {

	private static final int MIN_RESULTS = 0;

	private static final int MAX_RESULTS = 30;

	@Override
	public List<RentEntity> findPendinPaymentsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(RentRepository.QUERY_NAME.findPendinPaymentsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), RentEntity.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public List<RentEntity> fetchPendingOrIncompleteRentItemToLoadByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(RentRepository.QUERY_NAME.fetchPendingOrIncompleteRentItemToLoadByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), RentEntity.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public List<RentEntity> fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer(final String customerUuid, final EntityStatus entityStatus)
			throws BusinessException {
		return this.findByQuery(RentRepository.QUERY_NAME.fetchRentsWithPendingOrIncompleteRentItemToReturnByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process(), RentEntity.class)
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
	}

	@Override
	public RentEntity fetchByUuid(final String uuid) throws BusinessException {
		return this.findSingleByNamedQuery(RentRepository.QUERY_NAME.fetchByUuid, new ParamBuilder().add("uuid", uuid).process());
	}

	@Override
	public List<RentEntity> fetchWithIssuedGuidesByTypeAndCustomer(final GuideType guideType, final String customerUuid,
			final EntityStatus entityStatus)
					throws BusinessException {
		return this.findByNamedQuery(RentRepository.QUERY_NAME.fetchRentsWithIssuedGuidesByTypeAndCustomer,
				new ParamBuilder().add("guideType", guideType).add("customerUuid", customerUuid).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<RentEntity> fetchWithPaymentsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(RentRepository.QUERY_NAME.fetchRentsWithPaymentsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process())
				.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).setFirstResult(RentRepositoryImpl.MIN_RESULTS)
				.setMaxResults(RentRepositoryImpl.MAX_RESULTS)
				.getResultList();
	}

	@Override
	public Optional<RentEntity> findByCustomerAndUnitAndStatus(final String customerUuid, final String unitUuid, final RentStatus rentStatus,
			final EntityStatus entityStatus) throws BusinessException {
		try {
			final RentEntity rentEntity = this.findSingleByNamedQuery(RentRepository.QUERY_NAME.findByCustomerAndUnitAndStatus,
					new ParamBuilder().add("customerUuid", customerUuid)
					.add("unitUuid", unitUuid).add("rentStatus", rentStatus).add("entityStatus", entityStatus).process());

			return Optional.of(rentEntity);
		} catch (final NoResultException e) {
			return Optional.empty();
		}
	}
}
