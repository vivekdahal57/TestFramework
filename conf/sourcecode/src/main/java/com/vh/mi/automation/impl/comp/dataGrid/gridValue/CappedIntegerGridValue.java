package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;

/**
 * @author nimanandhar
 */
public class CappedIntegerGridValue extends IntegerGridValue implements IGridValue {

    public CappedIntegerGridValue(String value) {
        super(value);
    }

    @Override
    public int compareTo(IGridValue o) {
        if (value.matches("^>\\d+$")) {
            return 1;
        } else if (o.getValue().matches("^>\\d+$")) {
            return -1;
        } else
            return super.compareTo(o);
    }
}
