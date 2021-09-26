/**
 *
 */
package mz.co.grocery.core.rent.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.RentItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.util.ParamBuilder;

/**
 * @author Stélio Moiane
 *
 */

@Repository(RentItemDAOImpl.NAME)
public class RentItemDAOImpl extends GenericDAOImpl<RentItem, Long> implements RentItemDAO {

	public static final String NAME = "mz.co.grocery.core.rent.dao.RentItemDAOImpl";

	@Override
	public RentItem fetchByUuid(final String rentItemUuid) throws BusinessException {
		return this.findSingleByNamedQuery(RentItemDAO.QUERY_NAME.fetchByUuid,
				new ParamBuilder().add("rentItemUuid", rentItemUuid).process());
	}

}
