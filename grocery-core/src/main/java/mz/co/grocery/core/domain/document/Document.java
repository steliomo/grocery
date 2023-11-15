/**
 *
 */
package mz.co.grocery.core.domain.document;

import java.util.Collection;
import java.util.Map;

/**
 * @author Stélio Moiane
 *
 */
public interface Document {

	int LEFT_PAD = 5;

	char PAD_CHAR = '0';

	String PDF = "pdf";

	String getFilename();

	Map<String, Object> getParameters();

	Collection<?> getData();

	String getXml();
}
