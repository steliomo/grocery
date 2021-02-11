/**
 *
 */
package mz.co.grocery.core.product.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.product.model.ServiceDescription;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository(ServiceDescriptionDAOImpl.NAME)
public class ServiceDescriptionDAOImpl extends GenericDAOImpl<ServiceDescription, Long>
        implements ServiceDescriptionDAO {

	public static final String NAME = "mz.co.grocery.core.product.dao.ServiceDescriptionDAOImpl";

}
