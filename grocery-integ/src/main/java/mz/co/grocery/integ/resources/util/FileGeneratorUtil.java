/**
 *
 */
package mz.co.grocery.integ.resources.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Stélio Moiane
 *
 */
public class FileGeneratorUtil {

	public static final String FILE_DIR = "/opt/grocery/data/";

	public static void generatePdf(final String reportXmlName, final Map<String, Object> parameters, final Collection<?> dataSource,
			final String fileName) throws IOException, JRException {

		try (FileInputStream fileInputStream = new FileInputStream(FileGeneratorUtil.FILE_DIR + reportXmlName)) {

			final JasperReport jasperReport = JasperCompileManager.compileReport(fileInputStream);

			final JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);

			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, FileGeneratorUtil.FILE_DIR + fileName);
		}
	}
}
