package org.eu.leto.core.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;


public class AbstractModelObjectTest extends TestCase {
    public void testEqualsHashCode() {
        final SimpleModelObject smo1 = new SimpleModelObject("johndoe",
                createDate(1982, Calendar.FEBRUARY, 7));
        final SimpleModelObject smo2 = new SimpleModelObject("johndoe",
                createDate(1982, Calendar.FEBRUARY, 7));

        smo1.setId(1000L);
        smo2.setId(1000L);

        final String smo1Email = "johndoe@nowhere.com";
        final String smo2Email = "johndoe@nowhere.com";

        smo1.setEmails(new ArrayList<String>(Collections
                .singletonList(smo1Email)));
        smo2.setEmails(new ArrayList<String>(Collections
                .singletonList(smo2Email)));

        assertTrue(smo1.equals(smo1));
        assertTrue(smo1.hashCode() == smo1.hashCode());
        assertFalse(smo1.equals(null));
        assertTrue(smo1.equals(smo2));
        assertTrue(smo2.equals(smo2));
        assertTrue(smo1.hashCode() == smo2.hashCode());

        smo1.setId(null);
        assertTrue(smo1.equals(smo2));
        assertTrue(smo2.equals(smo1));
        assertTrue(smo1.hashCode() == smo2.hashCode());
    }


    private Date createDate(int year, int month, int day) {
        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
}
