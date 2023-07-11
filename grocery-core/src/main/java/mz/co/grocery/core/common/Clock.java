/**
 *
 */
package mz.co.grocery.core.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Stélio Moiane
 *
 */
public interface Clock {

	LocalDate todayDate();

	LocalDateTime todayDateTime();
}
