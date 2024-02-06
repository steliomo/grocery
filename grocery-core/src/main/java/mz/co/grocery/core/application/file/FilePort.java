/**
 *
 */
package mz.co.grocery.core.application.file;

import java.io.InputStream;

import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */
public interface FilePort {

	String FILE_DIR = "/opt/grocery/data/img";

	public String writeFile(final InputStream inputStream, final String fileLocation) throws BusinessException;

}
