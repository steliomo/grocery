/**
 *
 */
package mz.co.grocery.core.util;

/**
 * @author Stélio Moiane
 *
 */
public interface ApplicationTranslator {

	String getTranslation(String code);

	String getTranslation(String code, Object[] args);

}
