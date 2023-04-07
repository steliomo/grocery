/**
 *
 */
package mz.co.grocery.core.application.report;

import mz.co.grocery.core.domain.report.Report;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface ReportGeneratorPort {

	String FILE_DIR = "/opt/grocery/data/";

	void createPdfReport(Report report) throws BusinessException;
}
