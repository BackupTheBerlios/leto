package org.eu.leto.core.model.temporal;


import java.util.Calendar;


public enum Month {
    JANUARY(Calendar.JANUARY), FEBRUARY(Calendar.FEBRUARY), MARCH(
            Calendar.MARCH), APRIL(Calendar.APRIL), MAY(Calendar.MAY), JUNE(
            Calendar.JUNE), JULY(Calendar.JULY), AUGUST(Calendar.AUGUST),
    SEPTEMBER(Calendar.SEPTEMBER), OCTOBER(Calendar.OCTOBER), NOVEMBER(
            Calendar.NOVEMBER), DECEMBER(Calendar.DECEMBER);

    private final int javaValue;


    private Month(final int javaValue) {
        this.javaValue = javaValue;
    }


    public int getJavaValue() {
        return javaValue;
    }
}
