/**
 *
 */
package mz.co.grocery.core.file.service;

import mz.co.grocery.core.file.model.Report;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface FileGeneratorService {

	String FILE_DIR = "/opt/grocery/data/";

	void createPdfReport(Report report) throws BusinessException;
}
