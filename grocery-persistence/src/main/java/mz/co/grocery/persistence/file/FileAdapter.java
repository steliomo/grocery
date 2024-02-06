/**
 *
 */
package mz.co.grocery.persistence.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mz.co.grocery.core.application.file.FilePort;
import mz.co.grocery.core.common.PersistenceAdapter;
import mz.co.msaude.boot.frameworks.exception.BusinessException;

/**
 * @author Stélio Moiane
 *
 */

@PersistenceAdapter
public class FileAdapter implements FilePort {

	@Override
	public String writeFile(final InputStream inputStream, final String filename) throws BusinessException {

		final String fileLocation = FilePort.FILE_DIR + "/" + filename;

		try (OutputStream outputStream = new FileOutputStream(new File(fileLocation))) {

			final byte[] bytes = new byte[1024];
			int read = inputStream.read(bytes);

			while (read != -1) {
				outputStream.write(bytes, 0, read);
				read = inputStream.read(bytes);
			}

			outputStream.flush();
			outputStream.close();
		} catch (final IOException e) {
			e.printStackTrace();
			throw new BusinessException("The was an error writing the file");
		}

		return fileLocation;
	}
}
