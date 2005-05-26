package org.eu.leto.core.model.temporal;


import junit.framework.TestCase;


public class DateTest extends TestCase {
    public void testCompareTo() {
        final Date d1 = new Date(2005, Month.MAY, 25);
        final Date d2 = new Date(2005, Month.MAY, 30);
        assertEquals(-1, d1.compareTo(d2));
        assertEquals(1, d2.compareTo(d1));
        assertEquals(0, d1.compareTo(new Date(d1.getYear(), d1.getMonth(), d1
                .getDay())));
    }


    public void testEquals() {
        final Date d1 = new Date(2005, Month.MAY, 25);
        final Date d2 = new Date(2005, Month.MAY, 30);
        assertFalse(d1.equals(d2));
        assertTrue(d1.equals(d1));
        assertFalse(d1.equals(null));
        assertTrue(d1
                .equals(new Date(d1.getYear(), d1.getMonth(), d1.getDay())));
    }


    public void testHashCode() {
        final Date d1 = new Date(2005, Month.MAY, 25);
        final Date d2 = new Date(2005, Month.MAY, 30);
        assertFalse(d1.hashCode() == d2.hashCode());
        assertTrue(d1.hashCode() == new Date(d1.getYear(), d1.getMonth(), d1
                .getDay()).hashCode());
    }
}
