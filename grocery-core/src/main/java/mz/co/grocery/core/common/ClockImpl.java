/**
 *
 */
package mz.co.grocery.core.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

/**
 * @author Stélio Moiane
 *
 */
@Service
public class ClockImpl implements Clock {

	@Override
	public LocalDate todayDate() {
		return LocalDate.now();
	}

	@Override
	public LocalDateTime todayDateTime() {
		return LocalDateTime.now();
	}

}
