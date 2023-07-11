/**
 *
 */
package mz.co.grocery.core.application.guide.out;

import mz.co.grocery.core.domain.guide.GuideItem;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface GuideItemPort {

	GuideItem createGuideItem(UserContext userContext, GuideItem guideItem) throws BusinessException;

}
