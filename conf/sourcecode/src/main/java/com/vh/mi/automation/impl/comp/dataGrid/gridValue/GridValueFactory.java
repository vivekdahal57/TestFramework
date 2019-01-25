package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.constants.DataType;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public class GridValueFactory {

    public static IGridValue create(String value, DataType dataType) {
        switch (dataType) {
            case MONEY:
                return new MoneyGridValue(value);
            case INTEGER:
                return new IntegerGridValue(value);
            case DATE:
                return new DateGridValue(value);
            case DOUBLE:
                return new DoubleGridValue(value);
            case STRING:
                return new TextGridValue(value);
            case PERCENTAGE:
                return new PercentGridValue(value);
            case CAPPED_INTEGER:
                return new CappedIntegerGridValue(value);
            default:
                throw new RuntimeException("Unknown data type " + dataType);
        }
    }
}
