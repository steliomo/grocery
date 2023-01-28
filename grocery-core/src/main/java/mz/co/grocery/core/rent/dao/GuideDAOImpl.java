/**
 *
 */
package mz.co.grocery.core.rent.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.rent.model.Guide;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository(GuideDAOImpl.NAME)
public class GuideDAOImpl extends GenericDAOImpl<Guide, Long> implements GuideDAO {

	public static final String NAME = "mz.co.grocery.core.rent.dao.GuideDAOImpl";

}
