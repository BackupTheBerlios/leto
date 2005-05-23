package org.eu.leto.core;

import java.util.ResourceBundle;

public class ResourceBundleMessageRegisterTest extends
		AbstractMessageRegisterTest {
	@Override
	protected MessageRegister getMessageRegister() {
		final ResourceBundle resourceBundle = ResourceBundle
				.getBundle("org.eu.leto.core.MessageRegisterTest");
		return new ResourceBundleMessageRegister(resourceBundle);
	}
}
