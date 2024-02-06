/**
 *
 */
package mz.co.grocery.core.unit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.application.file.FilePort;
import mz.co.grocery.core.application.unit.in.UploadUnitLogoUseCase;
import mz.co.grocery.core.application.unit.out.UnitPort;
import mz.co.grocery.core.application.unit.service.UploadUnitLogoService;
import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.domain.unit.Unit;
import mz.co.grocery.core.fixturefactory.UnitTemplate;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
import mz.co.msaude.boot.frameworks.fixturefactory.EntityFactory;
import mz.co.msaude.boot.frameworks.model.UserContext;

/**
 * @author Stélio Moiane
 *
 */
public class UploadUnitLogoUseCaseTest extends AbstractUnitServiceTest {

	@Mock
	private UnitPort unitPort;

	@Mock
	private FilePort filePort;

	@InjectMocks
	private UploadUnitLogoUseCase uploadUnitLogoUseCase = new UploadUnitLogoService(this.unitPort, this.filePort);

	private InputStream inputStream;

	@Before
	public void setup() {

		try (InputStream input = new FileInputStream(FilePort.FILE_DIR + "/logo_super.png")) {

			this.inputStream = input;

		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldUploadUnitLogo() throws BusinessException {

		final UserContext context = this.getUserContext();
		final String filename = "logo_super.png";

		final Unit unitToUpdate = EntityFactory.gimme(Unit.class, UnitTemplate.VALID);
		Mockito.when(this.unitPort.findByUuid(unitToUpdate.getUuid())).thenReturn(unitToUpdate);
		Mockito.when(this.unitPort.updateUnit(context, unitToUpdate)).thenReturn(unitToUpdate);
		Mockito.when(this.filePort.writeFile(this.inputStream, filename)).thenReturn(FilePort.FILE_DIR + "/logo_super.png");

		final Unit unit = this.uploadUnitLogoUseCase.uploadLogo(context, this.inputStream, filename, unitToUpdate.getUuid());

		Mockito.verify(this.filePort, Mockito.times(1)).writeFile(this.inputStream, filename);
		Mockito.verify(this.unitPort, Mockito.times(1)).updateUnit(context, unitToUpdate);

		Assert.assertNotNull(unit.getLogoPath());
		Assert.assertEquals(FilePort.FILE_DIR + "/logo_super.png", unit.getLogoPath());
	}
}
