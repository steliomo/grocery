/**
 *
 */
package mz.co.grocery.core.guide.dao;

import org.springframework.stereotype.Repository;

import mz.co.grocery.core.guide.model.GuideItem;
import mz.co.msaude.boot.frameworks.dao.GenericDAOImpl;

/**
 * @author Stélio Moiane
 *
 */

@Repository(GuideItemDAOImpl.NAME)
public class GuideItemDAOImpl extends GenericDAOImpl<GuideItem, Long> implements GuideItemDAO {

	public static final String NAME = "mz.co.grocery.core.rent.dao.GuideItemDAOImpl";

}
