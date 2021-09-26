/**
 *
 */
package mz.co.grocery.core.rent.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.ReturnItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository(ReturnItemDAOImpl.NAME)
public class ReturnItemDAOImpl extends GenericDAOImpl<ReturnItem, Long> implements ReturnItemDAO {

	public static final String NAME = "mz.co.grocery.core.rent.dao.ReturnItemDAOImpl";

}
