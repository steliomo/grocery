/**
 *
 */
package mz.co.grocery.integ.resources.common;

import java.util.ArrayList;
import java.util.List;

import mz.co.grocery.core.util.ApplicationTranslator;

/**
 * @author Stélio Moiane
 *
 */
public class EnumsDTO<T> {

	private final T[] values;
	private final ApplicationTranslator applicationTranslator;

	public EnumsDTO(final ApplicationTranslator applicationTranslator, final T[] values) {
		this.applicationTranslator = applicationTranslator;
		this.values = values;
	}

	public List<EnumDTO> getValues() {
		final List<EnumDTO> values = new ArrayList<>();

		for (final T t : this.values) {
			values.add(new EnumDTO(t.toString(), this.applicationTranslator.getTranslation(t.toString())));
		}

		return values;
	}
}
