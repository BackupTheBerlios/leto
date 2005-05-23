package org.eu.leto.core;

import java.util.Locale;

import org.springframework.context.support.StaticMessageSource;

public class SpringMessageRegisterTest extends AbstractMessageRegisterTest {
	@Override
	protected MessageRegister getMessageRegister() {
		final StaticMessageSource messageSource = new StaticMessageSource();
		messageSource.addMessage("greetings", Locale.getDefault(), "Hello {0}");
		messageSource.addMessage("age", Locale.getDefault(), "23");
		messageSource.addMessage("male", Locale.getDefault(), "true");

		return new SpringMessageRegister(messageSource);
	}
}
