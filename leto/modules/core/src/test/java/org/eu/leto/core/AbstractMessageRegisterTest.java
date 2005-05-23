package org.eu.leto.core;

import junit.framework.TestCase;

public abstract class AbstractMessageRegisterTest extends TestCase {
	protected abstract MessageRegister getMessageRegister();

	public void testGetMessage() {
		final String msg = getMessageRegister().getMessage("greetings",
				"Alexandre");
		assertNotNull(msg);
		assertEquals("Hello Alexandre", msg);
	}

	public void testGetMessageUnknown() {
		final String msg = getMessageRegister().getMessage("test123");
		assertNull(msg);
	}

	public void testGetIntMessage() {
		final int i = getMessageRegister().getIntMessage("age");
		assertEquals(23, i);
	}

	public void testGetBooleanMessage() {
		final boolean male = getMessageRegister().getBooleanMessage("male");
		assertTrue(male);
	}
}
