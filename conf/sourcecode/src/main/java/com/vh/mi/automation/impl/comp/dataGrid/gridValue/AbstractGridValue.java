package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;

import java.text.DecimalFormat;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public abstract class AbstractGridValue implements IGridValue {
    public static final DecimalFormat FILTER_DECIMAL_FORMAT = new DecimalFormat("#0.##");
    protected final String value;

    public AbstractGridValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getFilterableValue() {
        checkState(isDataValid());
        return value;
    }

    @Override
    public boolean isNull() {
        return false;
    }


    protected boolean isNegative() {
        checkState(this instanceof INumericGridValue);
        return value.startsWith("(") && value.endsWith(")");
    }


    @Override
    public String toString() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        AbstractGridValue that = (AbstractGridValue) o;

        return !(value != null ? !value.equals(that.value) : that.value != null);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }


    static {
        FILTER_DECIMAL_FORMAT.setMaximumFractionDigits(2);
    }
}
