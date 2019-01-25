package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public class DoubleGridValue extends AbstractGridValue implements INumericGridValue {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##;(###,###.##)");

    public DoubleGridValue(String value) {
        super(value);
    }

    @Override
    public boolean isDataValid() {
        try {
            DECIMAL_FORMAT.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public int compareTo(IGridValue o) {
        Preconditions.checkArgument(o instanceof DoubleGridValue);
        DoubleGridValue comparedValue = (DoubleGridValue) o;

        Double value1 = Double.MIN_VALUE;
        if(isDataValid()) {
            value1 = parseDouble();
        }

        Double value2 = Double.MIN_VALUE;
        if (comparedValue.isDataValid()) {
            value2 = comparedValue.parseDouble();
        }

        return value1.compareTo(value2);
    }

    @Override
    public String getFilterableValue() {
        Preconditions.checkState(isDataValid());
        return FILTER_DECIMAL_FORMAT.format(parseDouble());
    }

    @Override
    public INumericGridValue plusOne() {
        String newValue = DECIMAL_FORMAT.format(parseDouble() + 1);
        return new DoubleGridValue(newValue);
    }

    @Override
    public INumericGridValue minusOne() {
        String newValue = DECIMAL_FORMAT.format(parseDouble() - 1);
        return new DoubleGridValue(newValue);
    }

    private Double parseDouble() {
        try {
            return DECIMAL_FORMAT.parse(value).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse to double " + value);
        }
    }
}
