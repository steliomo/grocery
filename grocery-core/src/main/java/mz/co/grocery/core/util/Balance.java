/**
 *
 */
package mz.co.grocery.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Stélio Moiane
 *
 */
@Component(Balance.NAME)
public class Balance {

	public static final String NAME = "mz.co.grocery.core.util.Balance";

	@Value("${balance.initial}")
	private String initial;

	@Value("${balance.bonus}")
	private String bonus;

	public String getInitial() {
		return this.initial;
	}

	public String getBonus() {
		return this.bonus;
	}
}
