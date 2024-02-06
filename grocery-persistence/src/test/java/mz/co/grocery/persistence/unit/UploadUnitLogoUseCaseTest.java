/**
 *
 */
package mz.co.grocery.persistence.unit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mz.co.grocery.core.application.file.FilePort;
import mz.co.grocery.core.application.unit.in.UploadUnitLogoUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.persistence.config.AbstractIntegServiceTest;
import mz.co.grocery.persistence.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;

/**
 * @author Stélio Moiane
 *
 */

public class UploadUnitLogoUseCaseTest extends AbstractIntegServiceTest {

	@Inject
	private UnitPort unitPort;

	@Inject
	private UploadUnitLogoUseCase uploadUnitLogoUseCase;

	private InputStream inputStream;

	private Unit unitToUpdate;

	@Before
	public void setup() throws BusinessException {

		try {
			this.inputStream = new FileInputStream("/opt/grocery/data/reports/logo_super.png");
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		this.unitToUpdate = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		this.unitToUpdate = this.unitPort.createUnit(this.getUserContext(), this.unitToUpdate);
	}

	@Test
	public void shouldUploadUnitLogo() throws BusinessException {

		final String filename = "logo_super.png";

		final Unit unit = this.uploadUnitLogoUseCase.uploadLogo(this.getUserContext(), this.inputStream, filename, this.unitToUpdate.getUuid());

		Assert.assertNotNull(unit.getLogoPath());
		Assert.assertEquals(FilePort.FILE_DIR + "/" + filename, unit.getLogoPath());
	}
}
