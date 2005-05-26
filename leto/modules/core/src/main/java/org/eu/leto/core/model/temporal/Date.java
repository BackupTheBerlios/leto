package org.eu.leto.core.model.temporal;


import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Embeddable
public class Date implements Comparable<Date>, Serializable {
    private Integer day;
    private Month month;
    private Integer year;


    public Date() {
        this(new GregorianCalendar());
    }


    public Date(final Calendar cal) {
        initFromCalendar(cal);
    }


    public Date(final java.util.Date javaDate) {
        final Calendar cal = new GregorianCalendar();
        cal.setTime(javaDate);
        initFromCalendar(cal);
    }


    public Date(final Integer year, final Month month, final Integer day) {
        this();
        setYear(year);
        setMonth(month);
        setDay(day);
    }


    private void initFromCalendar(Calendar cal) {
        setYear(cal.get(Calendar.YEAR));
        setDay(cal.get(Calendar.DATE));

        final int javaMonth = cal.get(Calendar.MONTH);
        for (final Month curMonth : Month.values()) {
            if (javaMonth == curMonth.getJavaValue()) {
                setMonth(curMonth);
                break;
            }
        }
    }


    public Integer getDay() {
        return day;
    }


    public void setDay(Integer day) {
        this.day = day;
    }


    public Month getMonth() {
        return month;
    }


    public void setMonth(Month month) {
        this.month = month;
    }


    public Integer getYear() {
        return year;
    }


    public void setYear(Integer year) {
        this.year = year;
    }


    public java.util.Date getJavaDate() {
        final Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month.getJavaValue());
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }


    public int compareTo(Date o) {
        return getJavaDate().compareTo(o.getJavaDate());
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
}
