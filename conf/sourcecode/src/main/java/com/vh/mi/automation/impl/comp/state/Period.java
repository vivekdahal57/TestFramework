package com.vh.mi.automation.impl.comp.state;

import com.vh.mi.automation.api.comp.state.IPeriod;
import org.joda.time.DateTime;

/**
 * @author i80448
 */
public class Period implements IPeriod {
    private final DateTime fromDate;
    private final DateTime toDate;

    public Period(DateTime from, DateTime to) {
        this.fromDate = from;
        this.toDate = to;
    }

    @Override
    public DateTime getFromDate() {
        return fromDate;
    }

    @Override
    public DateTime getToDate() {
        return toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (fromDate != null ? !fromDate.equals(period.fromDate) : period.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(period.toDate) : period.toDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromDate != null ? fromDate.hashCode() : 0;
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period [fromDate=" + fromDate + ", toDate=" + toDate + "]";
    }
}
