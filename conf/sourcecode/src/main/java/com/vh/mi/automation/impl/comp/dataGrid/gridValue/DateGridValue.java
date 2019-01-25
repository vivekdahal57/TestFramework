package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public class DateGridValue extends AbstractGridValue implements INumericGridValue {
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy");
    public static final DateTime INVALID_DATE = new DateTime(0, 1, 1, 1, 1);

    public DateGridValue(String value) {
        super(value);
    }

    @Override
    public boolean isDataValid() {
        try {
            DATE_TIME_FORMAT.parseDateTime(value);
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    @Override
    public int compareTo(IGridValue o) {
        Preconditions.checkArgument(o instanceof DateGridValue);
        DateGridValue comparedValue = (DateGridValue) o;

        DateTime value1 = INVALID_DATE;
        if (isDataValid()) {
            value1 = parseDateTime();
        }

        DateTime value2 = INVALID_DATE;
        if (comparedValue.isDataValid()) {
            value2 = comparedValue.parseDateTime();
        }

        return value1.compareTo(value2);
    }

    @Override
    public INumericGridValue plusOne() {
        return plusDays(1);
    }

    @Override
    public INumericGridValue minusOne() {
        return plusDays(-1);
    }

    private DateGridValue plusDays(int days) {
        DateTime newDateTime = parseDateTime().plusDays(days);
        String newValue = newDateTime.toString(DATE_TIME_FORMAT);
        return new DateGridValue(newValue);
    }

    private DateTime parseDateTime() {
        return DATE_TIME_FORMAT.parseDateTime(value);
    }
}
