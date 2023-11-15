/**
 *
 */
package mz.co.grocery.core.application.document;

import mz.co.grocery.core.domain.document.Document;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface DocumentGeneratorPort {

	String FILE_DIR = "/opt/grocery/data/";

	String generatePdfDocument(Document document) throws BusinessException;
}
