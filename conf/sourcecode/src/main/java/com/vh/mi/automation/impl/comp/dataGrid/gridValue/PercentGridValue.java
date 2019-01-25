package com.vh.mi.automation.impl.comp.dataGrid.gridValue;

import com.vh.mi.automation.api.comp.dataGrid.gridValue.IGridValue;
import com.vh.mi.automation.api.comp.dataGrid.gridValue.INumericGridValue;

import java.text.NumberFormat;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by i81131 on 4/2/2015.
 */
public class PercentGridValue extends AbstractGridValue implements INumericGridValue{
    public static final NumberFormat FORMAT = NumberFormat.getPercentInstance(Locale.US);

    public PercentGridValue(String value) {
        super(value);
    }

    @Override
    public boolean isDataValid() {
        return value.matches("^(-)?\\d{1,2}(\\.\\d{1,2})?%$");
    }

    @Override
    public int compareTo(IGridValue o) {
        Double value1 = Double.MIN_VALUE;
        if (isDataValid()) {
            value1 = Double.parseDouble(value.replace("%", ""));
        }

        Double value2 = Double.MIN_VALUE;
        if (o.isDataValid()) {
            value2 = Double.parseDouble(o.getValue().replace("%", ""));
        }

        return value1.compareTo(value2);
    }

    @Override
    public String getFilterableValue() {
        checkState(isDataValid());
        return parseDouble().toString();
    }


    @Override
    public INumericGridValue plusOne() {
        Double d = Double.valueOf(FILTER_DECIMAL_FORMAT.format(parseDouble() +1));
        String newValue = d.toString();
        return new PercentGridValue(newValue+"%");
    }

    @Override
    public INumericGridValue minusOne() {
        Double d = Double.valueOf(FILTER_DECIMAL_FORMAT.format(parseDouble() - 1));
        String newValue = d.toString();
        return new PercentGridValue(newValue+"%");
    }

    private Double parseDouble() {

            return Double.parseDouble(value.replace("%", ""));

    }
}
