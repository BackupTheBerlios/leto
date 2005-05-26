package org.eu.leto.core.model.temporal;


import junit.framework.TestCase;


public class DateRangeTest extends TestCase {
    public void testIncludesDate() {
        final DateRange range = new DateRange(new Date(2005, Month.MAY, 25),
                new Date(2005, Month.MAY, 30));
        assertTrue(range.includes(new Date(2005, Month.MAY, 27)));
        assertTrue(range.includes(new Date(2005, Month.MAY, 25)));
        assertTrue(range.includes(new Date(2005, Month.MAY, 30)));
        assertFalse(range.includes(new Date(2005, Month.MAY, 24)));
    }


    public void testExcludesDate() {
        final DateRange range = new DateRange(new Date(2005, Month.MAY, 25),
                new Date(2005, Month.MAY, 30));
        assertTrue(range.excludes(new Date(2005, Month.MAY, 24)));
        assertFalse(range.excludes(new Date(2005, Month.MAY, 27)));
        assertFalse(range.excludes(new Date(2005, Month.MAY, 25)));
        assertFalse(range.excludes(new Date(2005, Month.MAY, 30)));
    }
}
