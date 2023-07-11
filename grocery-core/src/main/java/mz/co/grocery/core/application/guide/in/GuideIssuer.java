/**
 *
 */
package mz.co.grocery.core.application.guide.in;

import mz.co.grocery.core.domain.guide.Guide;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface GuideIssuer {

	Guide issue(UserContext userContext, Guide guide) throws BusinessException;

}
