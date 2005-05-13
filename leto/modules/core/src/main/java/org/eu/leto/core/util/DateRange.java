

package org.eu.leto.core.util;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Embeddable
public class DateRange implements Comparable, Serializable {
    private Date end;
    private Date start;

    public DateRange() {
        this.start = this.end = new Date();
    }


    public DateRange(final Date start, final Date end) {
        this();
        setStart(start);
        setEnd(end);
    }

    public void setEnd(Date end) {
        if (end == null) {
            throw new NullArgumentException("end");
        }
        this.end = end;
    }


    @Column(nullable = false)
    public Date getEnd() {
        return end;
    }


    public void setStart(Date start) {
        if (start == null) {
            throw new NullArgumentException("start");
        }
        this.start = start;
    }


    @Column(nullable = false)
    public Date getStart() {
        return start;
    }


    public int compareTo(Object obj) {
        return CompareToBuilder.reflectionCompare(this, obj);
    }


    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }


    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }


    public boolean includes(DateRange range) {
        return includes(range.getStart()) && includes(range.getEnd());
    }


    public boolean includes(Date date) {
        if (date == null) {
            throw new NullArgumentException("date");
        }

        return !date.before(start) && !date.after(end);
    }


    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
