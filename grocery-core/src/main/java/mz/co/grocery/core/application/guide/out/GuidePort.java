/**
 *
 */
package mz.co.grocery.core.application.guide.out;

import mz.co.grocery.core.domain.guide.Guide;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface GuidePort {

	Guide createGuide(UserContext userContext, Guide guide) throws BusinessException;

}
