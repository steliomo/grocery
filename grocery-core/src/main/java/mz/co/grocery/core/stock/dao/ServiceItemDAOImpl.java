/**
 *
 */
package mz.co.grocery.core.stock.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.stock.model.ServiceItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.EntityStatus;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository(ServiceItemDAOImpl.NAME)
public class ServiceItemDAOImpl extends GenericDAOImpl<ServiceItem, Long> implements ServiceItemDAO {

	public static final String NAME = "mz.co.grocery.core.stock.dao.ServiceItemDAOImpl";

	@Override
	public List<ServiceItem> fetchAll(final int currentPage, final int maxResult, final EntityStatus entityStatus)
			throws BusinessException {
		final List<Long> serviceItemIds = this
				.findByQuery(ServiceItemDAO.QUERY_NAME.findAllIds, new ParamBuilder().add("entityStatus", entityStatus).process(),
						Long.class)
				.setFirstResult(currentPage * maxResult).setMaxResults(maxResult).getResultList();

		if (serviceItemIds.isEmpty()) {
			return new ArrayList<ServiceItem>();
		}

		return this
				.findByNamedQuery(ServiceItemDAO.QUERY_NAME.fetchAll, new ParamBuilder().add("serviceItemIds", serviceItemIds).process());
	}

	@Override
	public ServiceItem fetchByUuid(final String serviceItemUuid) throws BusinessException {
		return this.findSingleByNamedQuery(ServiceItemDAO.QUERY_NAME.fetchByUuid, new ParamBuilder().add("serviceItemUuid", serviceItemUuid).process());
	}

}
