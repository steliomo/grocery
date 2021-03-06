/**
 *
 */
package mz.co.grocery.core.item.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.grocery.model.Grocery;
import mz.co.grocery.core.item.model.Service;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */
@Repository(ServiceDAOImpl.NAME)
public class ServiceDAOImpl extends GenericDAOImpl<Service, Long> implements ServiceDAO {

	public static final String NAME = "mz.co.grocery.core.item.dao.ServiceDAOImpl";

	@Override
	public List<Service> findAll(final int currentPage, final int maxResult, final EntityStatus entityStatus) throws BusinessException {
		return this.findByQuery(ServiceDAO.QUERY_NAME.findAll, new ParamBuilder().add("entityStatus", entityStatus).process())
				.setFirstResult(currentPage * maxResult)
				.setMaxResults(maxResult).getResultList();
	}

	@Override
	public List<Service> findByName(final String serviceName, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceDAO.QUERY_NAME.findByName,
				new ParamBuilder().add("serviceName", "%" + serviceName + "%").add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Service> findByUnit(final Grocery unit, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceDAO.QUERY_NAME.findByUnit,
				new ParamBuilder().add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process());
	}

	@Override
	public List<Service> findNotInThisUnit(final Grocery unit, final EntityStatus entityStatus) throws BusinessException {
		return this.findByNamedQuery(ServiceDAO.QUERY_NAME.findNotInThisUnit,
				new ParamBuilder().add("unitUuid", unit.getUuid()).add("entityStatus", entityStatus).process());
	}
}
