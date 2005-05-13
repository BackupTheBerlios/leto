package org.eu.leto.realm;


import junit.framework.TestCase;


public class UserNotFoundExceptionTest extends TestCase {
    public void testGetLogin() {
        final String login = "root";
        final UserNotFoundException exc = new UserNotFoundException(login);
        assertEquals(login, exc.getLogin());

        final UserNotFoundException exc2 = new UserNotFoundException(null);
        assertNull(exc2.getLogin());
    }
}
