/**
 *
 */
package mz.co.grocery.core.domain.report;

import java.util.Collection;
import java.util.Map;

/**
 * @author Stélio Moiane
 *
 */
public interface Report {

	int LEFT_PAD = 5;

	char PAD_CHAR = '0';

	String getFileName();

	Map<String, Object> getParameters();

	Collection<?> getData();

	String getXml();
}
