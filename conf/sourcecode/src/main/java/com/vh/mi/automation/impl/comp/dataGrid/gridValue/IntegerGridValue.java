package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public class IntegerGridValue extends AbstractGridValue implements INumericGridValue {
    public static final String POSITIVE_INTEGER_REGEX = "^\\d+[\\d,]*$";
    public static final String NEGATIVE_INTEGER_REGEX = "^[(]\\d+[\\d,]*[)]$";
    public static final String NEGATIVE_INTEGER_REGEX_PATTERN_ALTERNATE = "^-\\d+[\\d,]*$";

    public IntegerGridValue(String value) {
        super(value);
        if (value.matches(NEGATIVE_INTEGER_REGEX_PATTERN_ALTERNATE)) {
            throw new RuntimeException("Negative integer represented as -number instead of (number). Value=" + value);
        }
    }

    @Override
    public boolean isDataValid() {
        return value.matches(POSITIVE_INTEGER_REGEX) || value.matches(NEGATIVE_INTEGER_REGEX);
    }

    @Override
    public int compareTo(IGridValue o) {
        Preconditions.checkArgument(o instanceof IntegerGridValue);
        IntegerGridValue comparedValue = (IntegerGridValue) o;

        Integer value1 = Integer.MIN_VALUE;
        if (isDataValid()) {
            value1 = parseInteger();
        }

        Integer value2 = Integer.MIN_VALUE;
        if (comparedValue.isDataValid()) {
            value2 = comparedValue.parseInteger();
        }

        return value1.compareTo(value2);
    }

    @Override
    public String getFilterableValue() {
        checkState(isDataValid());
        return FILTER_DECIMAL_FORMAT.format(parseInteger());
    }

    @Override
    public INumericGridValue plusOne() {
        String newValue = getStringRepresentation(parseInteger() + 1);
        return new IntegerGridValue(newValue);
    }

    @Override
    public INumericGridValue minusOne() {
        String newValue = getStringRepresentation(parseInteger() - 1);
        return new IntegerGridValue(newValue);
    }

    public String getStringRepresentation(int value) {
       if(value>=0)
           return String.valueOf(value);
        else
           return "(" + String.valueOf(-value) + ")";
    }

    private int parseInteger() {
        if (isNegative())
            return -Integer.parseInt(value.replaceAll("[$,()]", ""));
        else
            return Integer.parseInt(value.replaceAll("[$,()]", ""));
    }

}
