/**
 *
 */
package mz.co.grocery.persistence.quotation.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.persistence.quotation.entity.QuotationEntity;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository
public class QuotationRepositoryImpl extends GenericDAOImpl<QuotationEntity, Long> implements QuotationRepository {

	@Override
	public List<QuotationEntity> fetchQuotationsByCustomer(final String customerUuid, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(QuotationRepository.QUERY_NAME.fetchQuotationsByCustomer,
				new ParamBuilder().add("customerUuid", customerUuid).add("entityStatus", entityStatus).process());
	}
}
