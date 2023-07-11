/**
 *
 */
package mz.co.grocery.core.util;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * @author Stélio Moiane
 *
 */
@Service(ApplicationTranslatorImpl.NAME)
public class ApplicationTranslatorImpl implements ApplicationTranslator {

	public static final String NAME = "mz.co.grocery.core.util.ApplicationTranslatorImpl";

	public static final String PT = "pt";

	@Inject
	private MessageSource messageSource;

	@Override
	public String getTranslation(final String code) {
		return this.messageSource.getMessage(code, null, new Locale(ApplicationTranslatorImpl.PT));
	}

	@Override
	public String getTranslation(final String code, final String... args) {
		return this.messageSource.getMessage(code, args, new Locale(ApplicationTranslatorImpl.PT));
	}
}
