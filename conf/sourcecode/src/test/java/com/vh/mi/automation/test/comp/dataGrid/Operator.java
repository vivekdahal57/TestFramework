package com.vh.mi.automation.test.comp.dataGrid;

import com.google.common.base.Preconditions;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;

/**
 * Created by nimanandhar on 11/19/2014.
 */
public enum Operator {
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_OR_EQUALS("<="),
    GREATER_OR_EQUALS(">="),
    EQUALS("="),
    BETWEEN("<>");

    private String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
