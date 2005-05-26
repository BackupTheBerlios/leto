package org.eu.leto.core.model;


import javax.persistence.Column;

import org.apache.commons.lang.NullArgumentException;


public abstract class AbstractRange<T extends Comparable<T>> extends
        AbstractModelObject {
    private T start;
    private T end;


    public AbstractRange() {
        super();
    }


    public AbstractRange(final T start, final T end) {
        this();
        setStart(start);
        setEnd(end);
    }


    @Column(nullable = false)
    public T getEnd() {
        return end;
    }


    public void setEnd(T end) {
        if (end == null) {
            throw new NullArgumentException("end");
        }
        this.end = end;
    }


    @Column(nullable = false)
    public T getStart() {
        return start;
    }


    public void setStart(T start) {
        if (start == null) {
            throw new NullArgumentException("start");
        }
        this.start = start;
    }


    public boolean includes(T o) {
        return o != null && o.compareTo(start) > -1 && o.compareTo(end) <= 1;
    }


    public boolean excludes(T o) {
        return o == null || (o.compareTo(start) == -1 || o.compareTo(end) == 1);
    }


    public boolean includes(AbstractRange<T> range) {
        return range != null && includes(range.start) && includes(range.end);
    }


    public boolean excludes(AbstractRange<T> range) {
        return range == null
                || ((excludes(range.start) && excludes(range.end)) && !range
                        .overlaps(this));
    }


    public boolean overlaps(AbstractRange<T> range) {
        return range != null && (range.includes(start) || range.includes(end));
    }
}
