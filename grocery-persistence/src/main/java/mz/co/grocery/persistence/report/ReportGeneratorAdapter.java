/**
 *
 */
package mz.co.grocery.persistence.report;

import java.io.FileInputStream;
import java.io.IOException;

import mz.co.grocery.core.application.report.ReportGeneratorPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.report.Report;
import mz.co.msaude.boot.frameworks.exception.BusinessException;
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
@PersistenceAdapter
public class ReportGeneratorAdapter implements ReportGeneratorPort {

	public static final String FILE_DIR = "/opt/grocery/data/";

	@Override
	public void createPdfReport(final Report report) throws BusinessException {

		try (FileInputStream fileInputStream = new FileInputStream(ReportGeneratorAdapter.FILE_DIR + report.getXml())) {

			final JasperReport jasperReport = JasperCompileManager.compileReport(fileInputStream);

			final JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(report.getData());

			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, report.getParameters(), jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, ReportGeneratorAdapter.FILE_DIR + report.getFileName());
		} catch (final IOException | JRException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
}
