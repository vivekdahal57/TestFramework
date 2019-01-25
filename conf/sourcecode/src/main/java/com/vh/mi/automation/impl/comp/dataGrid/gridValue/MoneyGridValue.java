package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public class MoneyGridValue extends AbstractGridValue implements INumericGridValue {
    public static final NumberFormat FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    public MoneyGridValue(String value) {
        super(value);
    }

    @Override
    public boolean isDataValid() {
        try {
            FORMAT.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public int compareTo(IGridValue o) {
        Preconditions.checkArgument(o instanceof MoneyGridValue);
        MoneyGridValue comparedValue = (MoneyGridValue) o;

        Double value1 = Double.MIN_VALUE;
        if (isDataValid()) {
            value1 = parseDouble();
        }

        Double value2 = Double.MIN_VALUE;
        if (isDataValid()) {
            value2 = comparedValue.parseDouble();
        }
        return value1.compareTo(value2);
    }

    @Override
    public String getFilterableValue() {
        checkState(isDataValid());
        return FILTER_DECIMAL_FORMAT.format(parseDouble());
    }

    @Override
    public INumericGridValue plusOne() {
        String newValue = FORMAT.format(parseDouble() + 1);
        return new MoneyGridValue(newValue);
    }

    @Override
    public INumericGridValue minusOne() {
        String newValue = FORMAT.format(parseDouble() - 1);
        return new MoneyGridValue(newValue);
    }

    private Double parseDouble() {
        try {
            return FORMAT.parse(value).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException("Parse exception for value " + value);
        }
    }
}
