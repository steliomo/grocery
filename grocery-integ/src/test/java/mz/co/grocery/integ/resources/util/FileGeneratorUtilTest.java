/**
 *
 */
package mz.co.grocery.integ.resources.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import mz.co.grocery.integ.resources.rent.dto.GuideItemDTO;
import net.sf.jasperreports.engine.JRException;

/**
 * @author Stélio Moiane
 *
 */
public class FileGeneratorUtilTest {

	@Test
	@Ignore
	public void shouldGeneratePdf() throws IOException, JRException {

		final String reportXmlName = "reports/quotation.jrxml";
		final Map<String, Object> parameters = new HashMap<>();
		final List<GuideItemDTO> dataSource = new ArrayList<>();
		final String fileName = "report.pdf";

		FileGeneratorUtil.generatePdf(reportXmlName, parameters, dataSource, fileName);

		final File file = new File(FileGeneratorUtil.FILE_DIR + fileName);

		Assert.assertTrue(file.exists());
	}

}
