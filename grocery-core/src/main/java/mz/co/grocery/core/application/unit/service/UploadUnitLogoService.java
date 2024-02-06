/**
 *
 */
package mz.co.grocery.core.application.unit.service;

import java.io.InputStream;

import mz.co.grocery.core.application.file.FilePort;
import mz.co.grocery.core.application.unit.in.UploadUnitLogoUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.common.UseCase;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */

@UseCase
public class UploadUnitLogoService implements UploadUnitLogoUseCase {

	private UnitPort unitPort;

	private FilePort filePort;

	public UploadUnitLogoService(final UnitPort unitPort, final FilePort filePort) {
		this.unitPort = unitPort;
		this.filePort = filePort;
	}

	@Override
	public Unit uploadLogo(final UserContext context, final InputStream inputStream, final String filename, final String unitUuid)
			throws BusinessException {

		Unit unit = this.unitPort.findByUuid(unitUuid);

		final String fileLocation = this.filePort.writeFile(inputStream, filename);

		unit.setLogoPath(fileLocation);

		unit = this.unitPort.updateUnit(context, unit);

		return unit;
	}
}
