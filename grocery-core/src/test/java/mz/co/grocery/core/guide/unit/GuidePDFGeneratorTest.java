/**
 *
 */
package mz.co.grocery.core.guide.unit;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import mz.co.grocery.core.config.AbstractUnitServiceTest;
import mz.co.grocery.core.file.service.FileGeneratorService;
import mz.co.grocery.core.file.service.FileGeneratorServiceImpl;
import mz.co.grocery.core.guide.builder.GuideUnitBuider;
import mz.co.grocery.core.guide.model.Guide;
import mz.co.grocery.core.guide.model.GuideReport;
import mz.co.grocery.core.guide.model.GuideType;
import mz.co.grocery.core.util.ApplicationTranslator;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public class GuidePDFGeneratorTest extends AbstractUnitServiceTest {

	@InjectMocks
	private final FileGeneratorService fileGeneratorService = new FileGeneratorServiceImpl();

	@Mock
	private ApplicationTranslator translator;

	@Test
	public void shouldCreatePdfReport() throws BusinessException {
		final Guide guide = new GuideUnitBuider(GuideType.TRANSPORT.toString()).withSaleProducts(50).build();
		guide.setIssueDate(LocalDate.now());
		guide.setCreatedAt(LocalDateTime.now());
		guide.setId(100L);

		Mockito.when(this.translator.getTranslation(ArgumentMatchers.any())).thenReturn("Guia de transporte".toUpperCase());

		final GuideReport report = new GuideReport(guide, this.translator);

		this.fileGeneratorService.createPdfReport(report);

		Assert.assertTrue(new File("/opt/grocery/data/" + report.getFileName()).exists());
	}
}
