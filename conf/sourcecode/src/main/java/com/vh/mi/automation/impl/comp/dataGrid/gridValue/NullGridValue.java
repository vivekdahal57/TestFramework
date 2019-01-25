package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;

/**
 * Created by nimanandhar on 11/19/2014.
 * Null Object Pattern
 */
public class NullGridValue extends AbstractGridValue implements INumericGridValue {
    public static final NullGridValue NULL_GRID_VALUE = new NullGridValue("<<NULL>>");

    public NullGridValue(String value) {
        super(value);
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public int compareTo(IGridValue o) {
        return 0;
    }

    @Override
    public boolean isDataValid() {
        return false;
    }

    @Override
    public INumericGridValue plusOne() {
        return NULL_GRID_VALUE;
    }

    @Override
    public INumericGridValue minusOne() {
        return NULL_GRID_VALUE;
    }
}
