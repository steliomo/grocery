/**
 *
 */
package mz.co.grocery.integ.resources.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * @author Stélio Moiane
 *
 */
public class EnumsDTO<T> {

	private final T[] values;
	private final MessageSource messageSource;

	public EnumsDTO(final MessageSource messageSource, final T[] values) {
		this.messageSource = messageSource;
		this.values = values;
	}

	public List<EnumDTO> getValues() {
		final List<EnumDTO> values = new ArrayList<>();

		for (final T t : this.values) {
			values.add(new EnumDTO(t.toString(), this.messageSource.getMessage(t.toString(), null, new Locale("pt"))));
		}

		return values;
	}
}
