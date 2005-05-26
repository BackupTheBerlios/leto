package org.eu.leto.core.model.temporal;


import javax.persistence.Embeddable;

import org.eu.leto.core.model.AbstractRange;


@Embeddable
public class DateRange extends AbstractRange<Date> {
    public DateRange() {
        super();
    }


    public DateRange(final Date start, final Date end) {
        this();
        setStart(start);
        setEnd(end);
    }
}
