/**
 *
 */
package mz.co.grocery.core.product.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository(ServiceDescriptionDAOImpl.NAME)
public class ServiceDescriptionDAOImpl extends GenericDAOImpl<ServiceDescription, Long>
implements ServiceDescriptionDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ServiceDescriptionDAOImpl";

	@Override
	public List<ServiceDescription> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(ServiceDescriptionDAO.QUERY_NAME.findAll, new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();
	}

	@Override
	public ServiceDescription fetchByUuid(final String serviceDescriptionUuid) throws BusinessException {
		return this.findSingleByNamedQuery(ServiceDescriptionDAO.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("serviceDescriptionUuid", serviceDescriptionUuid).process());
	}
}
