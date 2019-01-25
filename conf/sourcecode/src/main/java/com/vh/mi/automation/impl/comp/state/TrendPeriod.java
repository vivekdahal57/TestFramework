package com.vh.mi.automation.impl.comp.state;

import com.vh.mi.automation.api.comp.state.IPeriod;
import com.vh.mi.automation.api.comp.state.ITrendPeriod;

/**
 * @author i80448
 */
public class TrendPeriod implements ITrendPeriod {
    private final IPeriod period1;
    private final IPeriod period2;

    public TrendPeriod(IPeriod period1, IPeriod period2) {
        this.period1 = period1;
        this.period2 = period2;
    }

    @Override
    public IPeriod getPeriod1() {
        return period1;
    }

    @Override
    public IPeriod getPeriod2() {
        return period2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrendPeriod that = (TrendPeriod) o;

        if (period1 != null ? !period1.equals(that.period1) : that.period1 != null) return false;
        if (period2 != null ? !period2.equals(that.period2) : that.period2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = period1 != null ? period1.hashCode() : 0;
        result = 31 * result + (period2 != null ? period2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrendPeriod{" +
                "period1=" + period1 +
                ", period2=" + period2 +
                '}';
    }
}
