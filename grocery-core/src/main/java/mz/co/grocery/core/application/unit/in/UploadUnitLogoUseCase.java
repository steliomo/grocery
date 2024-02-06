/**
 *
 */
package mz.co.grocery.core.application.unit.in;

import java.io.InputStream;

import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public interface UploadUnitLogoUseCase {

	Unit uploadLogo(UserContext context, InputStream inputStream, String filename, String unitUuid) throws BusinessException;

}
