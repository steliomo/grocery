/**
 *
 */
package mz.co.grocery.persistence.document;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import mz.co.grocery.core.application.document.DocumentGeneratorPort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.grocery.core.domain.document.Document;
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
public class DocumentGeneratorAdapter implements DocumentGeneratorPort {

	@Override
	public String generatePdfDocument(final Document report) throws BusinessException {

		try (FileInputStream fileInputStream = new FileInputStream(DocumentGeneratorPort.FILE_DIR + report.getXml())) {

			final JasperReport jasperReport = JasperCompileManager.compileReport(fileInputStream);

			final JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(report.getData());

			final Map<String, Object> parameters = report.getParameters();

			parameters.entrySet().forEach(param -> {

				final Object value = param.getValue();

				if (value instanceof Collection) {
					param.setValue(new JRBeanCollectionDataSource((Collection<?>) value));
				}
			});

			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, DocumentGeneratorPort.FILE_DIR + report.getFilename());
		} catch (final IOException | JRException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

		return report.getFilename();
	}
}
