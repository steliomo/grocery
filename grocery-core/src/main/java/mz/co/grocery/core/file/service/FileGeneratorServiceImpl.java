/**
 *
 */
package mz.co.grocery.core.file.service;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import mz.co.grocery.core.file.model.Report;
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
@Service(FileGeneratorServiceImpl.NAME)
public class FileGeneratorServiceImpl implements FileGeneratorService {

	public static final String NAME = "mz.co.grocery.core.file.service.FileGeneratorServiceImpl";

	@Override
	public void createPdfReport(final Report report) throws BusinessException {

		try (FileInputStream fileInputStream = new FileInputStream(FileGeneratorService.FILE_DIR + report.getXml())) {

			final JasperReport jasperReport = JasperCompileManager.compileReport(fileInputStream);

			final JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(report.getData());

			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, report.getParameters(), jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint, FileGeneratorService.FILE_DIR + report.getFileName());
		} catch (final IOException | JRException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
}
