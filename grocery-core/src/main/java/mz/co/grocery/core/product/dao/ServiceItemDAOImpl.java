/**
 *
 */
package mz.co.grocery.core.product.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.ServiceItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository(ServiceItemDAOImpl.NAME)
public class ServiceItemDAOImpl extends GenericDAOImpl<ServiceItem, Long> implements ServiceItemDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ServiceItemDAOImpl";

}
