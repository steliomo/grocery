/**
 *
 */
package mz.co.grocery.core.util;

import java.time.LocalDate;

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

}
