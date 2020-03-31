/**
 *
 */
package mz.co.grocery.integ.resources.util;

import org.hibernate.Hibernate;

/**
 * @author Stélio Moiane
 *
 */
public class ProxyUtil {

	public static <T> boolean isInitialized(final T proxy) {
		return (proxy != null) && Hibernate.isInitialized(proxy);
	}
}
